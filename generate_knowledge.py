#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
大学生心理健康科普知识生成脚本
根据心理分类自动生成科普知识并写入数据库
"""

import json
import mysql.connector
from mysql.connector import pooling
import requests
from datetime import datetime

# 数据库配置
DB_CONFIG = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': 'root',
    'database': 'youth_mental_health'
}

# AI 配置
AI_CONFIG = {
    'base_url': 'https://dashscope.aliyuncs.com/compatible-mode/v1',
    'api_key': 'sk-7673104b8c0f4d6a88b7e5dfaed283df',
    'model': 'qwen-plus'
}

# 心理分类列表（根据图片中的分类）
CATEGORIES = [
    {'id': 1, 'name': '情绪管理', 'icon': '/uploads/2026/02/06/40fd37ca8bd64118a35bbdabbf3987ce.png'},
    {'id': 2, 'name': '人际关系', 'icon': '/uploads/2026/02/06/69c0fe1158a04e15b38a0f9481eeeebf.png'},
    {'id': 3, 'name': '学习压力', 'icon': '/uploads/2026/02/06/b43faa151db64ea59281bc3a348d3d8e.png'},
    {'id': 4, 'name': '自我认知', 'icon': '/uploads/2026/02/06/e9979022a1af414cb773491e9791ffe2.png'},
    {'id': 5, 'name': '亲子关系', 'icon': '/uploads/2026/02/06/2d4c329fccd4466cb8501120d644dfa1.png'},
    {'id': 6, 'name': '睡眠健康', 'icon': '/uploads/2026/02/06/d8350927a1b841ab9ad845926c85133f.png'},
    {'id': 7, 'name': '网络心理', 'icon': '/uploads/2026/02/06/f3e4e95a189b4a2cbbb0455dd00575dc.png'},
    {'id': 8, 'name': '青春期心理', 'icon': '/uploads/2026/02/06/927cbfac0bd3457284def7d44b24d6c1.png'}
]

# 每个分类生成的文章数量
ARTICLES_PER_CATEGORY = 3


def create_db_pool():
    """创建数据库连接池"""
    return pooling.MySQLConnectionPool(
        pool_name='knowledge_pool',
        pool_size=5,
        **DB_CONFIG
    )


def generate_article_content(category_name):
    """使用 AI 生成科普文章内容"""
    
    # 系统提示词
    system_prompt = """你是一位专业的大学生心理健康教育专家。请根据指定的主题，撰写一篇专业、科学且易于理解的心理健康科普文章。

文章要求：
1. 结构清晰，包含 2-3 个二级标题（使用<h2>标签）
2. 内容专业但通俗易懂，适合大学生阅读
3. 提供实用的建议和技巧
4. 字数在 800-1500 字之间
5. 使用 HTML 标签格式化内容（<p>、<h2>、<ul>、<li>等）
6. 语气温暖、有同理心，避免说教
7. 不要使用过于专业的术语，如必须使用请解释

请直接返回文章内容，不要有其他说明文字。"""

    # 用户提示词
    user_prompt = f"""请为大学生群体撰写一篇关于"{category_name}"的心理健康科普文章。

内容应包含：
1. 该主题的基本概念和重要性
2. 大学生在该方面常见的困扰或问题
3. 科学、实用的应对方法和建议
4. 何时需要寻求专业帮助

文章标题请单独返回，格式如下：
标题：[你的标题]
内容：[你的文章内容]"""

    try:
        headers = {
            'Authorization': f'Bearer {AI_CONFIG["api_key"]}',
            'Content-Type': 'application/json'
        }
        
        payload = {
            'model': AI_CONFIG['model'],
            'messages': [
                {'role': 'system', 'content': system_prompt},
                {'role': 'user', 'content': user_prompt}
            ],
            'temperature': 0.7,
            'max_tokens': 2000
        }
        
        response = requests.post(
            f"{AI_CONFIG['base_url']}/chat/completions",
            headers=headers,
            json=payload,
            timeout=60
        )
        
        if response.status_code == 200:
            result = response.json()
            content = result['choices'][0]['message']['content']
            return content
        else:
            print(f"AI 请求失败：{response.status_code}")
            return None
            
    except Exception as e:
        print(f"生成内容时出错：{e}")
        return None


def parse_ai_response(content):
    """解析 AI 返回的内容，提取标题和正文"""
    if not content:
        return None, None
    
    # 尝试提取标题
    title = None
    article = content
    
    if '标题：' in content:
        lines = content.split('\n')
        for i, line in enumerate(lines):
            if line.startswith('标题：'):
                title = line.replace('标题：', '').strip()
                article = '\n'.join(lines[i+1:])
                break
    
    # 如果没有找到标题，使用内容的前 50 个字作为标题
    if not title:
        title = content[:50].replace('\n', '')
    
    # 清理内容
    article = article.replace('内容：', '').strip()
    
    return title, article


def generate_summary(content):
    """从内容生成摘要"""
    # 提取纯文本（去除 HTML 标签）
    import re
    text = re.sub(r'<[^>]+>', '', content)
    # 取前 100 个字作为摘要
    summary = text[:100].replace('\n', '') + '...'
    return summary


def insert_knowledge(conn, category_id, title, summary, content, sort=1):
    """插入科普知识到数据库"""
    cursor = conn.cursor()
    
    try:
        # 检查是否已存在相同标题的文章
        check_sql = "SELECT id FROM psy_knowledge WHERE title = %s"
        cursor.execute(check_sql, (title,))
        if cursor.fetchone():
            print(f"  ⚠️  文章已存在：{title}")
            return False
        
        # 插入新知识
        insert_sql = """
        INSERT INTO psy_knowledge 
        (category_id, title, cover, summary, content, view_count, collect_count, 
         status, is_hot, sort, create_time, update_time)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, NOW(), NOW())
        """
        
        # 封面图使用分类图标
        cover = CATEGORIES[category_id - 1]['icon'] if category_id <= len(CATEGORIES) else '/uploads/default.jpg'
        
        cursor.execute(insert_sql, (
            category_id,
            title,
            cover,
            summary,
            content,
            0,  # view_count
            0,  # collect_count
            1,  # status (上架)
            0,  # is_hot (不热门)
            sort
        ))
        
        conn.commit()
        return True
        
    except Exception as e:
        conn.rollback()
        print(f"  ❌ 插入失败：{e}")
        return False
    finally:
        cursor.close()


def main():
    """主函数"""
    print("=" * 60)
    print("大学生心理健康科普知识生成工具")
    print("=" * 60)
    print()
    
    # 创建数据库连接
    print("📊 连接数据库...")
    try:
        db_pool = create_db_pool()
        conn = db_pool.get_connection()
        print("✅ 数据库连接成功")
    except Exception as e:
        print(f"❌ 数据库连接失败：{e}")
        return
    
    print()
    print(f"📚 分类数量：{len(CATEGORIES)}")
    print(f"📝 每个分类生成：{ARTICLES_PER_CATEGORY} 篇文章")
    print(f"📖 预计生成总数：{len(CATEGORIES) * ARTICLES_PER_CATEGORY} 篇")
    print()
    
    total_created = 0
    total_failed = 0
    
    for category in CATEGORIES:
        category_id = category['id']
        category_name = category['name']
        
        print(f"\n{'='*60}")
        print(f"📌 正在处理分类：【{category_name}】(ID: {category_id})")
        print(f"{'='*60}")
        
        for i in range(ARTICLES_PER_CATEGORY):
            print(f"\n  ✍️  生成第 {i+1}/{ARTICLES_PER_CATEGORY} 篇文章...")
            
            # 生成内容
            ai_response = generate_article_content(category_name)
            
            if not ai_response:
                print(f"  ❌ AI 生成失败")
                total_failed += 1
                continue
            
            # 解析内容
            title, content = parse_ai_response(ai_response)
            
            if not title:
                print(f"  ❌ 解析标题失败")
                total_failed += 1
                continue
            
            # 生成摘要
            summary = generate_summary(content)
            
            print(f"  📄 标题：{title[:30]}...")
            
            # 插入数据库
            if insert_knowledge(conn, category_id, title, summary, content, i+1):
                print(f"  ✅ 插入成功")
                total_created += 1
            else:
                total_failed += 1
        
        # 每处理完一个分类，等待一下避免 API 限流
        import time
        time.sleep(2)
    
    # 关闭连接
    conn.close()
    
    print()
    print("=" * 60)
    print("🎉 生成完成！")
    print(f"✅ 成功：{total_created} 篇")
    print(f"❌ 失败：{total_failed} 篇")
    print("=" * 60)


if __name__ == '__main__':
    main()
