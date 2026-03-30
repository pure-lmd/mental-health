/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : youth_mental_health

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 07/02/2026 17:44:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for psy_activity
-- ----------------------------
DROP TABLE IF EXISTS `psy_activity`;
CREATE TABLE `psy_activity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '活动描述',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '活动详情',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '活动地址',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `max_num` int(11) NULL DEFAULT 0 COMMENT '最大报名人数',
  `current_num` int(11) NULL DEFAULT 0 COMMENT '当前报名人数',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0下架/1报名中/2进行中/3已结束)',
  `is_hot` tinyint(4) NULL DEFAULT 0 COMMENT '是否热门(0否/1是)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status_hot`(`status`, `is_hot`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_activity
-- ----------------------------
INSERT INTO `psy_activity` VALUES (1, '春季心理健康周——心理讲座', '/uploads/2026/02/06/0098b4e0944e41c5b9f7f0a02858a9c1.jpg', '专业心理咨询师带来心理健康主题讲座，帮助同学们了解心理健康知识。', '<h2>活动简介</h2><p>本次讲座邀请资深心理咨询师为大家讲解青少年常见心理问题及应对方法。</p><h2>讲座内容</h2><p>1. 认识情绪与压力</p><p>2. 学会自我调节</p><p>3. 建立良好的人际关系</p>', '学校大礼堂', '2026-03-01 14:00:00', '2026-03-01 16:00:00', 200, 3, 1, 1, '2026-02-03 00:00:00', '2026-02-07 13:47:58');
INSERT INTO `psy_activity` VALUES (2, '心理健康团体辅导活动', '/uploads/2026/02/06/65d16f7b1cc045a59f609243d606d237.jpg', '通过团体活动的形式，帮助同学们增强人际交往能力，缓解压力。', '<h2>活动简介</h2><p>团体辅导是一种有效的心理干预方式，通过集体活动促进成员间的互动和成长。</p><h2>活动内容</h2><p>1. 破冰游戏</p><p>2. 主题讨论</p><p>3. 角色扮演</p><p>4. 分享与总结</p>', '心理咨询中心', '2026-03-05 15:00:00', '2026-03-05 17:00:00', 30, 2, 1, 1, '2026-02-03 00:00:00', '2026-02-06 22:49:15');
INSERT INTO `psy_activity` VALUES (3, '心理电影赏析：《心灵捕手》', '/uploads/2026/02/06/f91c03ad9ea2485eb73208d81fb828ec.jpg', '通过观看心理题材电影，引导同学们思考心理健康的重要性。', '<h2>活动简介</h2><p>《心灵捕手》是一部经典的心理电影，讲述了一位天才少年在心理咨询师帮助下成长的故事。</p><h2>活动安排</h2><p>1. 电影放映（约2小时）</p><p>2. 观后讨论（约30分钟）</p>', '多媒体教室', '2026-03-10 19:00:00', '2026-03-10 21:30:00', 100, 2, 1, 0, '2026-02-03 00:00:00', '2026-02-06 22:49:15');
INSERT INTO `psy_activity` VALUES (4, '压力管理工作坊', '/uploads/2026/02/06/cbeb2c60de454d6db0877ed43f5dc381.jpg', '学习实用的压力管理技巧，帮助同学们更好地应对学习和生活压力。', '<h2>活动简介</h2><p>本工作坊将教授多种实用的压力管理技巧。</p><h2>活动内容</h2><p>1. 压力来源分析</p><p>2. 放松训练</p><p>3. 时间管理技巧</p><p>4. 正念冥想入门</p>', '心理咨询中心', '2026-03-15 14:00:00', '2026-03-15 16:30:00', 40, 2, 1, 0, '2026-02-03 00:00:00', '2026-02-06 22:49:15');
INSERT INTO `psy_activity` VALUES (5, '亲子沟通主题讲座', '/uploads/2026/02/06/f1aaefdd41274a06b7973fe98ad63427.jpg', '邀请学生和家长共同参与，学习有效的亲子沟通技巧。', '<h2>活动简介</h2><p>亲子关系是影响青少年心理健康的重要因素，本讲座将帮助家长和孩子学会更好地沟通。</p><h2>讲座内容</h2><p>1. 理解青春期特点</p><p>2. 有效沟通的技巧</p><p>3. 建立信任关系</p>', '学校报告厅', '2026-03-20 14:00:00', '2026-03-20 16:00:00', 150, 1, 1, 1, '2026-02-03 00:00:00', '2026-02-06 22:49:15');

-- ----------------------------
-- Table structure for psy_ai_chat
-- ----------------------------
DROP TABLE IF EXISTS `psy_ai_chat`;
CREATE TABLE `psy_ai_chat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `session_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会话ID',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色(user/assistant)',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_session_id`(`session_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI对话记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_ai_chat
-- ----------------------------
INSERT INTO `psy_ai_chat` VALUES (1, 1, 'session_001', 'user', '你好，我最近感觉压力很大，不知道该怎么办。', '2026-02-03 08:00:00');
INSERT INTO `psy_ai_chat` VALUES (2, 1, 'session_001', 'assistant', '你好！听到你说感觉压力很大，我很理解你的感受。压力是很多人都会经历的，能告诉我是什么方面的压力让你感到困扰吗？是学习、人际关系还是其他方面呢？', '2026-02-03 08:00:05');
INSERT INTO `psy_ai_chat` VALUES (3, 1, 'session_001', 'user', '主要是学习压力，考试快到了，感觉自己准备得不够好。', '2026-02-03 08:01:00');
INSERT INTO `psy_ai_chat` VALUES (4, 1, 'session_001', 'assistant', '考试前感到压力是很正常的，这说明你很重视这次考试。不过过度的压力可能会影响你的发挥。我有几个建议：1. 制定合理的复习计划，把大任务分解成小目标；2. 保证充足的睡眠和适当的休息；3. 尝试一些放松技巧，比如深呼吸；4. 相信自己已经付出的努力。你觉得哪个建议对你最有帮助？', '2026-02-03 08:01:10');
INSERT INTO `psy_ai_chat` VALUES (5, 2, 'session_002', 'user', '我和同学关系不太好，不知道怎么交朋友。', '2026-02-03 09:00:00');
INSERT INTO `psy_ai_chat` VALUES (6, 2, 'session_002', 'assistant', '人际交往确实是很多人面临的挑战。能和我说说具体是什么情况吗？是觉得难以开口和别人说话，还是和同学相处时会有冲突？', '2026-02-03 09:00:05');
INSERT INTO `psy_ai_chat` VALUES (7, 2, 'session_002', 'user', '我比较内向，不太敢主动和别人说话。', '2026-02-03 09:01:00');
INSERT INTO `psy_ai_chat` VALUES (8, 2, 'session_002', 'assistant', '内向是一种性格特点，不是缺点。很多内向的人都有很深厚的友谊。你可以尝试：1. 从小事开始，比如对同学微笑、打招呼；2. 找到共同兴趣爱好的同学，有话题更容易交流；3. 参加一些小组活动，在活动中自然地认识朋友；4. 做一个好的倾听者，真诚地关心他人。不用着急，慢慢来，交朋友需要时间。', '2026-02-03 09:01:10');
INSERT INTO `psy_ai_chat` VALUES (9, 3, 'session_003', 'user', '我最近睡眠不好，经常失眠。', '2026-02-03 10:00:00');
INSERT INTO `psy_ai_chat` VALUES (10, 3, 'session_003', 'assistant', '失眠确实很困扰人。能告诉我你的失眠情况是怎样的吗？是难以入睡、容易醒来，还是醒得太早？另外，你晚上睡前通常在做什么呢？', '2026-02-03 10:00:05');
INSERT INTO `psy_ai_chat` VALUES (11, 3, 'session_003', 'user', '主要是难以入睡，躺在床上会想很多事情。睡前一般在玩手机。', '2026-02-03 10:01:00');
INSERT INTO `psy_ai_chat` VALUES (12, 3, 'session_003', 'assistant', '我理解你的情况。睡前玩手机和脑子里想很多事情都会影响睡眠质量。我建议你：1. 睡前1小时不要使用手机和电脑，屏幕的蓝光会抑制褪黑素分泌；2. 可以试试写睡前日记，把脑子里的想法写下来，让大脑\"清空\"；3. 尝试一些放松技巧，比如听舒缓的音乐或做深呼吸练习；4. 保持规律的作息时间。坚持一段时间，相信你的睡眠会改善的！', '2026-02-03 10:01:10');
INSERT INTO `psy_ai_chat` VALUES (13, 11, 'dde581680fd543e7aa264a9579857eaf', 'user', '你好', '2026-02-06 21:31:54');
INSERT INTO `psy_ai_chat` VALUES (14, 11, 'dde581680fd543e7aa264a9579857eaf', 'assistant', '你好呀～👋 很开心见到你！我是你的“心理小助手”，一个愿意认真听你说、陪你一起面对情绪和烦恼的朋友 🌟\n\n不管你现在是有点小困扰、最近压力大、心情低落、和家人朋友闹了别扭，还是只是想找个安全的地方说说话……我都在这里，不评判、不着急，只带着关心和耐心听你讲。\n\n你今天感觉怎么样？😊  \n（如果暂时不想说也没关系，我们可以慢慢来～）', '2026-02-06 21:31:58');
INSERT INTO `psy_ai_chat` VALUES (15, 11, '49be36a4e2b14d86b61ae6181b61243f', 'user', '你好', '2026-02-06 21:43:43');
INSERT INTO `psy_ai_chat` VALUES (16, 11, '49be36a4e2b14d86b61ae6181b61243f', 'assistant', '你好呀～👋 很开心见到你！我是你的“心理小助手”，一个愿意认真听你说、陪你一起面对情绪和成长小烦恼的朋友 🌟\n\n不管今天你心里装着开心的事、有点小困扰，还是只是想随便聊聊——我都准备好啦，带着耐心和温暖等你开口 😊  \n你想说点什么呢？🌱', '2026-02-06 21:43:46');
INSERT INTO `psy_ai_chat` VALUES (17, 11, 'ecbb601a3c7149fc9206b27c8034a945', 'user', '我有点难过', '2026-02-06 21:44:12');
INSERT INTO `psy_ai_chat` VALUES (18, 11, 'ecbb601a3c7149fc9206b27c8034a945', 'assistant', '谢谢你愿意告诉我你有点难过，这其实需要很大的勇气呢 🌟  \n有时候情绪就像天空中的云，来来去去，不一定非要马上“赶走”它，我们可以先轻轻陪着它，也陪着你自己。\n\n如果你愿意的话，可以和我聊聊：  \n☁️ 是最近发生了什么事吗？  \n☁️ 还是心里一直压着一些说不出口的感觉？  \n☁️ 或者只是今天特别累、特别孤单、特别没力气？\n\n不用着急回答，也不用觉得“这点小事不值得难过”。你的感受很重要，真实又珍贵。  \n我会在这里认真听，不评判，不打断，只陪着你。\n\n如果你想安静一会儿，我们也可以一起深呼吸几次（试试：吸气4秒→屏住2秒→呼气6秒），让身体稍微放松一点点 🌿\n\n你不是一个人。💛  \n我在这儿。', '2026-02-06 21:44:16');

-- ----------------------------
-- Table structure for psy_category
-- ----------------------------
DROP TABLE IF EXISTS `psy_category`;
CREATE TABLE `psy_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类图标',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0禁用/1启用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '心理分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_category
-- ----------------------------
INSERT INTO `psy_category` VALUES (1, '情绪管理', '/uploads/2026/02/06/40fd37ca8bd64118a35bbdabbf3987ce.png', 1, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (2, '人际关系', '/uploads/2026/02/06/69c0fe1158a04e15b38a0f9481eeeebf.png', 2, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (3, '学习压力', '/uploads/2026/02/06/b43faa151db64ea59281bc3a348d3d8e.png', 3, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (4, '自我认知', '/uploads/2026/02/06/e9979022a1af414cb773491e9791ffe2.png', 4, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (5, '亲子关系', '/uploads/2026/02/06/2d4c329fccd4466cb8501120d644dfa1.png', 5, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (6, '睡眠健康', '/uploads/2026/02/06/d8350927a1b841ab9ad845926c85133f.png', 6, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (7, '网络心理', '/uploads/2026/02/06/f3e4e95a189b4a2cbbb0455dd00575dc.png', 7, 1, '2026-02-03 00:00:00');
INSERT INTO `psy_category` VALUES (8, '青春期心理', '/uploads/2026/02/06/927cbfac0bd3457284def7d44b24d6c1.png', 8, 1, '2026-02-03 00:00:00');

-- ----------------------------
-- Table structure for psy_collect
-- ----------------------------
DROP TABLE IF EXISTS `psy_collect`;
CREATE TABLE `psy_collect`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID',
  `type` tinyint(4) NOT NULL COMMENT '类型(1科普/2宣传/3资讯)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_target_type`(`target_id`, `type`) USING BTREE,
  INDEX `idx_user_type`(`user_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_collect
-- ----------------------------
INSERT INTO `psy_collect` VALUES (1, 1, 1, 1, '2026-02-03 08:00:00');
INSERT INTO `psy_collect` VALUES (2, 1, 3, 1, '2026-02-03 08:30:00');
INSERT INTO `psy_collect` VALUES (3, 2, 2, 1, '2026-02-03 09:00:00');
INSERT INTO `psy_collect` VALUES (4, 2, 5, 1, '2026-02-03 09:30:00');
INSERT INTO `psy_collect` VALUES (5, 3, 1, 2, '2026-02-03 10:00:00');
INSERT INTO `psy_collect` VALUES (6, 3, 8, 1, '2026-02-03 10:30:00');
INSERT INTO `psy_collect` VALUES (7, 4, 1, 3, '2026-02-03 11:00:00');
INSERT INTO `psy_collect` VALUES (8, 4, 4, 1, '2026-02-03 11:30:00');
INSERT INTO `psy_collect` VALUES (9, 5, 2, 2, '2026-02-03 12:00:00');
INSERT INTO `psy_collect` VALUES (10, 5, 6, 1, '2026-02-03 12:30:00');
INSERT INTO `psy_collect` VALUES (11, 6, 3, 3, '2026-02-03 13:00:00');
INSERT INTO `psy_collect` VALUES (12, 6, 7, 1, '2026-02-03 13:30:00');
INSERT INTO `psy_collect` VALUES (13, 7, 4, 2, '2026-02-03 14:00:00');
INSERT INTO `psy_collect` VALUES (14, 7, 1, 1, '2026-02-03 14:30:00');
INSERT INTO `psy_collect` VALUES (15, 8, 5, 3, '2026-02-03 15:00:00');

-- ----------------------------
-- Table structure for psy_comment
-- ----------------------------
DROP TABLE IF EXISTS `psy_comment`;
CREATE TABLE `psy_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `target_id` bigint(20) NOT NULL COMMENT '目标ID',
  `type` tinyint(4) NOT NULL COMMENT '类型(1科普/2宣传/3资讯)',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父评论ID(回复)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0隐藏/1显示)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_target_type`(`target_id`, `type`) USING BTREE,
  INDEX `idx_target_status`(`target_id`, `type`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_comment
-- ----------------------------
INSERT INTO `psy_comment` VALUES (1, 1, 1, 1, '这篇文章写得很好，让我对情绪管理有了更深的认识！', 0, 1, '2026-02-03 08:00:00');
INSERT INTO `psy_comment` VALUES (2, 2, 1, 1, '学到了很多实用的方法，感谢分享！', 0, 1, '2026-02-03 08:30:00');
INSERT INTO `psy_comment` VALUES (3, 3, 1, 1, '回复@张三：我也觉得这篇文章很有帮助', 1, 1, '2026-02-03 09:00:00');
INSERT INTO `psy_comment` VALUES (4, 4, 2, 1, '人际交往技巧真的很重要，以前不太会处理人际关系', 0, 1, '2026-02-03 09:30:00');
INSERT INTO `psy_comment` VALUES (5, 5, 3, 1, '学习压力确实很大，文章说的方法我要试试', 0, 1, '2026-02-03 10:00:00');
INSERT INTO `psy_comment` VALUES (6, 6, 3, 1, '希望能有更多关于学习压力的文章', 0, 1, '2026-02-03 10:30:00');
INSERT INTO `psy_comment` VALUES (7, 7, 5, 1, '和父母沟通确实是个难题，这篇文章很及时', 0, 1, '2026-02-03 11:00:00');
INSERT INTO `psy_comment` VALUES (8, 8, 8, 1, '青春期真的很迷茫，感谢这篇文章的指导', 0, 1, '2026-02-03 11:30:00');
INSERT INTO `psy_comment` VALUES (9, 1, 1, 3, '这个资讯让我了解到心理健康的重要性', 0, 1, '2026-02-03 12:00:00');
INSERT INTO `psy_comment` VALUES (10, 2, 2, 3, '国家越来越重视心理健康了，这是好事', 0, 1, '2026-02-03 12:30:00');
INSERT INTO `psy_comment` VALUES (11, 3, 1, 2, '宣传做得很好，希望更多人关注心理健康', 0, 1, '2026-02-03 13:00:00');
INSERT INTO `psy_comment` VALUES (12, 4, 2, 2, '525心理健康日的活动很有意义', 0, 1, '2026-02-03 13:30:00');

-- ----------------------------
-- Table structure for psy_feedback
-- ----------------------------
DROP TABLE IF EXISTS `psy_feedback`;
CREATE TABLE `psy_feedback`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '反馈内容',
  `images` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片(JSON数组)',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态(0待回复/1已回复)',
  `reply` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '回复内容',
  `reply_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '反馈表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_feedback
-- ----------------------------
INSERT INTO `psy_feedback` VALUES (1, 1, '建议增加更多心理测试', '希望系统能增加更多类型的心理测试，比如性格测试、职业倾向测试等。', '[\"https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3\"]', 1, '感谢您的建议，我们会考虑增加更多类型的心理测试，请持续关注系统更新！', '2026-02-03 10:00:00', '2026-02-03 08:00:00');
INSERT INTO `psy_feedback` VALUES (2, 2, '系统使用体验很好', '系统界面简洁，使用方便，心理科普内容也很丰富，非常感谢！', NULL, 1, '感谢您的肯定，我们会继续努力，为您提供更好的服务！', '2026-02-03 11:00:00', '2026-02-03 09:00:00');
INSERT INTO `psy_feedback` VALUES (3, 3, '建议增加预约咨询功能', '希望能在系统中直接预约心理咨询师进行一对一咨询。', NULL, 0, NULL, NULL, '2026-02-03 10:00:00');
INSERT INTO `psy_feedback` VALUES (4, 4, '活动报名问题', '报名活动后没有收到确认通知，不确定是否报名成功。', '[\"https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3\"]', 1, '您好，经查询您的报名已成功，请在\"我的预约\"中查看。后续我们会优化报名通知功能，感谢您的反馈！', '2026-02-03 14:00:00', '2026-02-03 11:00:00');
INSERT INTO `psy_feedback` VALUES (5, 5, 'AI助手回答很有帮助', '和AI助手聊了很多，感觉得到了很大的帮助，希望AI助手能更智能一些。', NULL, 1, '感谢您的反馈，我们会持续优化AI助手的智能水平，为您提供更好的服务！', '2026-02-03 15:00:00', '2026-02-03 12:00:00');
INSERT INTO `psy_feedback` VALUES (6, 11, '123', '6666666666666666', NULL, 1, '欧克', '2026-02-07 15:16:14', '2026-02-07 13:55:52');

-- ----------------------------
-- Table structure for psy_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `psy_knowledge`;
CREATE TABLE `psy_knowledge`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摘要',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  `collect_count` int(11) NULL DEFAULT 0 COMMENT '收藏量',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0下架/1上架)',
  `is_hot` tinyint(4) NULL DEFAULT 0 COMMENT '是否热门(0否/1是)',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE,
  INDEX `idx_status_hot`(`status`, `is_hot`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '心理科普表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_knowledge
-- ----------------------------
INSERT INTO `psy_knowledge` VALUES (1, 1, '如何正确认识和管理情绪', '/uploads/2026/02/06/548136dc9cff430f9f8cd25dbc7af182.jpg', '情绪是人类心理活动的重要组成部分，学会正确认识和管理情绪对青少年的健康成长至关重要。', '<h2>一、什么是情绪</h2><p>情绪是人对客观事物的态度体验，是人脑对客观事物与主体需要之间关系的反映。</p><h2>二、情绪的种类</h2><p>基本情绪包括：喜悦、悲伤、愤怒、恐惧、惊讶、厌恶等。</p><h2>三、如何管理情绪</h2><p>1. 认识情绪：首先要学会识别自己的情绪状态。</p><p>2. 接纳情绪：任何情绪都是正常的，不要否定自己的感受。</p><p>3. 表达情绪：用适当的方式表达情绪，如与朋友倾诉、写日记等。</p><p>4. 调节情绪：通过深呼吸、运动、听音乐等方式调节负面情绪。</p>', 261, 2, 1, 1, 1, '2026-02-03 00:00:00', '2026-02-07 17:43:08');
INSERT INTO `psy_knowledge` VALUES (2, 2, '青少年人际交往技巧', '/uploads/2026/02/06/42b7285bd9fc4457b2a58c8b3da41312.jpg', '良好的人际关系是青少年健康成长的重要保障，掌握人际交往技巧有助于建立和谐的人际关系。', '<h2>一、人际交往的重要性</h2><p>人际交往能力是一个人适应社会的重要能力，良好的人际关系能给我们带来支持和快乐。</p><h2>二、人际交往的基本技巧</h2><p>1. 学会倾听：认真听取他人的想法和感受。</p><p>2. 真诚待人：以真诚的态度对待每一个人。</p><p>3. 换位思考：站在对方的角度考虑问题。</p><p>4. 适度表达：学会表达自己的想法，但不要过于强势。</p><h2>三、处理人际冲突</h2><p>冲突是正常的，关键是如何解决。建议采用沟通协商的方式，而非对抗或回避。</p>', 201, 1, 1, 1, 2, '2026-02-03 00:00:00', '2026-02-07 17:21:38');
INSERT INTO `psy_knowledge` VALUES (3, 3, '学习压力的应对策略', '/uploads/2026/02/06/757a20dd2ca9474e89c81bab32d87c2a.jpg', '学习压力是青少年面临的主要压力来源之一，学会正确应对学习压力对学业和身心健康都很重要。', '<h2>一、认识学习压力</h2><p>适度的学习压力能够激发学习动力，但过度的压力则会影响学习效果和身心健康。</p><h2>二、学习压力的来源</h2><p>1. 考试压力</p><p>2. 作业负担</p><p>3. 升学压力</p><p>4. 家长期望</p><h2>三、应对策略</h2><p>1. 合理规划时间，提高学习效率。</p><p>2. 设定合理目标，避免过高期望。</p><p>3. 学会放松，适当休息和娱乐。</p><p>4. 寻求帮助，与老师、家长沟通。</p>', 315, 1, 1, 1, 3, '2026-02-03 00:00:00', '2026-02-06 22:57:42');
INSERT INTO `psy_knowledge` VALUES (4, 4, '认识真实的自己', '/uploads/2026/02/06/cc0bc12e5b7b4a09806d4210e00bc3e4.jpg', '自我认知是心理健康的基础，只有正确认识自己，才能更好地发展自己。', '<h2>一、什么是自我认知</h2><p>自我认知是指个体对自己的认识和了解，包括对自己的能力、性格、情感等方面的认识。</p><h2>二、自我认知的维度</h2><p>1. 生理自我：对自己身体特征的认识。</p><p>2. 心理自我：对自己心理特征的认识。</p><p>3. 社会自我：对自己社会角色的认识。</p><h2>三、如何提升自我认知</h2><p>1. 自我反思：经常思考自己的行为和想法。</p><p>2. 接受反馈：虚心听取他人的意见。</p><p>3. 尝试新事物：在新体验中发现自己。</p>', 178, 1, 1, 0, 4, '2026-02-03 00:00:00', '2026-02-06 22:57:42');
INSERT INTO `psy_knowledge` VALUES (5, 5, '如何与父母有效沟通', '/uploads/2026/02/06/9fc72c3d4ca949d79287f8ca03c3b093.jpg', '亲子关系是青少年最重要的人际关系之一，学会与父母有效沟通是建立良好亲子关系的关键。', '<h2>一、亲子沟通的重要性</h2><p>良好的亲子沟通能够促进家庭和谐，帮助青少年健康成长。</p><h2>二、沟通中常见的问题</h2><p>1. 代沟：两代人之间的观念差异。</p><p>2. 缺乏理解：双方难以站在对方角度思考。</p><p>3. 沟通方式不当：命令式、指责式沟通。</p><h2>三、有效沟通的技巧</h2><p>1. 选择合适的时机和地点。</p><p>2. 用\"我\"的语言表达感受。</p><p>3. 耐心倾听父母的想法。</p><p>4. 寻找共同话题。</p>', 234, 1, 1, 0, 5, '2026-02-03 00:00:00', '2026-02-06 22:57:42');
INSERT INTO `psy_knowledge` VALUES (6, 6, '青少年睡眠健康指南', '/uploads/2026/02/06/16ffc7ffe3f84670a587986b9afdbe02.jpg', '充足的睡眠对青少年的身心健康和学习效率至关重要，本文将介绍青少年睡眠的相关知识。', '<h2>一、睡眠的重要性</h2><p>睡眠是人体恢复精力、巩固记忆的重要过程，青少年每天需要8-10小时的睡眠。</p><h2>二、影响睡眠的因素</h2><p>1. 电子设备使用过度</p><p>2. 学业压力大</p><p>3. 作息不规律</p><p>4. 饮食不当</p><h2>三、改善睡眠的方法</h2><p>1. 建立规律的作息时间。</p><p>2. 睡前避免使用电子设备。</p><p>3. 创造舒适的睡眠环境。</p><p>4. 适当运动，但避免睡前剧烈运动。</p>', 157, 1, 1, 0, 6, '2026-02-03 00:00:00', '2026-02-06 22:57:42');
INSERT INTO `psy_knowledge` VALUES (7, 7, '正确使用网络的心理指南', '/uploads/2026/02/06/9776a57863944a0eb7a067c0eb85aa73.jpg', '网络已成为青少年生活的重要组成部分，如何正确使用网络关系到青少年的身心健康。', '<h2>一、网络的双面性</h2><p>网络既是学习工具，也可能带来负面影响，关键在于如何正确使用。</p><h2>二、网络使用中的常见问题</h2><p>1. 网络成瘾</p><p>2. 网络霸凌</p><p>3. 信息过载</p><p>4. 隐私泄露</p><h2>三、健康使用网络的建议</h2><p>1. 控制上网时间。</p><p>2. 选择有益内容。</p><p>3. 保护个人隐私。</p><p>4. 遇到问题及时求助。</p>', 198, 1, 1, 0, 7, '2026-02-03 00:00:00', '2026-02-06 22:57:42');
INSERT INTO `psy_knowledge` VALUES (8, 8, '青春期心理变化与适应', '/uploads/2026/02/06/3494ed4bbe5d429caa22381a5384561e.jpg', '青春期是人生的重要转折期，了解青春期的心理变化有助于更好地适应这一阶段。', '<h2>一、青春期的特点</h2><p>青春期是从儿童向成人过渡的时期，伴随着生理和心理的巨大变化。</p><h2>二、青春期常见的心理变化</h2><p>1. 情绪波动大</p><p>2. 自我意识增强</p><p>3. 渴望独立</p><p>4. 对异性产生兴趣</p><h2>三、如何适应青春期</h2><p>1. 了解青春期是正常的成长阶段。</p><p>2. 接纳自己的变化。</p><p>3. 与家长、老师保持沟通。</p><p>4. 培养健康的兴趣爱好。</p>', 269, 1, 1, 1, 8, '2026-02-03 00:00:00', '2026-02-06 22:57:42');

-- ----------------------------
-- Table structure for psy_news
-- ----------------------------
DROP TABLE IF EXISTS `psy_news`;
CREATE TABLE `psy_news`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摘要',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0下架/1上架)',
  `is_hot` tinyint(4) NULL DEFAULT 0 COMMENT '是否热门(0否/1是)',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status_hot`(`status`, `is_hot`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '资讯表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_news
-- ----------------------------
INSERT INTO `psy_news` VALUES (1, '青少年心理健康问题引起社会广泛关注', '/uploads/2026/02/06/671392d481a74d74a85febb201444d59.jpg', '近年来，青少年心理健康问题日益突出，社会各界纷纷呼吁加强青少年心理健康教育。', '<p>近年来，随着社会竞争的加剧和学业压力的增大，青少年心理健康问题日益突出。</p><p>据统计，我国青少年心理问题发生率呈上升趋势，抑郁、焦虑等心理问题在青少年中越来越常见。</p><p>专家呼吁，学校、家庭和社会应该共同关注青少年心理健康，为青少年创造良好的成长环境。</p>', 'Ti', 238, 1, 1, 1, '2026-02-03 00:00:00', '2026-02-07 17:39:37');
INSERT INTO `psy_news` VALUES (2, '教育部发布加强学生心理健康管理工作的通知', '/uploads/2026/02/06/0b63dee5061346798739c60abc6275b4.jpg', '教育部要求各地各校切实加强学生心理健康管理工作，保障学生身心健康发展。', '<p>教育部近日发布通知，要求各地各校进一步加强学生心理健康管理工作。</p><p>通知强调，要建立健全学生心理健康教育体系，配备专业心理教师，开展心理健康教育课程。</p><p>同时，要建立学生心理危机预警机制，及时发现和干预学生心理问题。</p>', 'Ti', 193, 1, 1, 2, '2026-02-03 00:00:00', '2026-02-07 13:38:33');
INSERT INTO `psy_news` VALUES (3, '心理咨询师缺口巨大，专业人才培养迫在眉睫', '/uploads/2026/02/06/3655bf7d5b7d438c9f0aa4c65b788a67.jpg', '我国心理咨询师数量远远不能满足社会需求，专业人才培养亟需加强。', '<p>随着心理健康意识的提高，心理咨询需求日益增长，但我国心理咨询师数量却远远不能满足需求。</p><p>据了解，我国每万人口心理咨询师数量远低于发达国家水平。</p><p>专家建议，应加大心理咨询专业人才的培养力度，提高心理咨询服务的可及性。</p>', 'Ti', 157, 1, 0, 3, '2026-02-03 00:00:00', '2026-02-06 20:57:52');
INSERT INTO `psy_news` VALUES (4, '研究发现：运动有助于改善青少年心理健康', '/uploads/2026/02/06/69d6e20449ef4558bf06e709ffeca85d.jpg', '最新研究表明，规律运动能够显著改善青少年的心理健康状况。', '<p>一项最新研究发现，规律的体育运动能够显著改善青少年的心理健康状况。</p><p>研究显示，每周进行3次以上中等强度运动的青少年，抑郁和焦虑的发生率明显降低。</p><p>专家建议青少年每天至少进行60分钟的体育活动，以促进身心健康。</p>', 'Ti', 198, 1, 0, 4, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_news` VALUES (5, '家长如何帮助孩子应对考试焦虑', '/uploads/2026/02/06/407fe838c95649b6bf726876d2a21c5a.jpg', '考试焦虑是很多学生面临的问题，家长可以通过以下方法帮助孩子应对。', '<p>考试焦虑是很多学生在面对考试时常见的心理反应。</p><p>家长可以通过以下方法帮助孩子应对：</p><p>1. 不要过度强调考试的重要性</p><p>2. 帮助孩子建立合理的期望</p><p>3. 教孩子一些放松技巧</p><p>4. 保证孩子的睡眠和饮食</p>', 'Ti', 267, 1, 1, 5, '2026-02-03 00:00:00', '2026-02-06 16:38:03');

-- ----------------------------
-- Table structure for psy_option
-- ----------------------------
DROP TABLE IF EXISTS `psy_option`;
CREATE TABLE `psy_option`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `question_id` bigint(20) NOT NULL COMMENT '题目ID',
  `label` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '选项标签(A/B/C/D)',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '选项内容',
  `score` int(11) NULL DEFAULT 0 COMMENT '选项分值',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_question_id`(`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 201 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试选项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_option
-- ----------------------------
INSERT INTO `psy_option` VALUES (1, 1, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (2, 1, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (3, 1, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (4, 1, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (5, 2, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (6, 2, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (7, 2, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (8, 2, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (9, 3, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (10, 3, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (11, 3, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (12, 3, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (13, 4, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (14, 4, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (15, 4, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (16, 4, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (17, 5, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (18, 5, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (19, 5, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (20, 5, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (21, 6, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (22, 6, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (23, 6, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (24, 6, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (25, 7, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (26, 7, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (27, 7, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (28, 7, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (29, 8, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (30, 8, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (31, 8, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (32, 8, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (33, 9, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (34, 9, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (35, 9, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (36, 9, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (37, 10, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (38, 10, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (39, 10, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (40, 10, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (41, 11, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (42, 11, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (43, 11, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (44, 11, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (45, 12, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (46, 12, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (47, 12, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (48, 12, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (49, 13, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (50, 13, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (51, 13, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (52, 13, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (53, 14, 'A', '总是', 1, 1);
INSERT INTO `psy_option` VALUES (54, 14, 'B', '经常', 2, 2);
INSERT INTO `psy_option` VALUES (55, 14, 'C', '偶尔', 3, 3);
INSERT INTO `psy_option` VALUES (56, 14, 'D', '从不', 5, 4);
INSERT INTO `psy_option` VALUES (57, 15, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (58, 15, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (59, 15, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (60, 15, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (61, 16, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (62, 16, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (63, 16, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (64, 16, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (65, 17, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (66, 17, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (67, 17, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (68, 17, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (69, 18, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (70, 18, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (71, 18, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (72, 18, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (73, 19, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (74, 19, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (75, 19, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (76, 19, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (77, 20, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (78, 20, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (79, 20, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (80, 20, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (81, 21, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (82, 21, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (83, 21, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (84, 21, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (85, 22, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (86, 22, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (87, 22, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (88, 22, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (89, 23, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (90, 23, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (91, 23, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (92, 23, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (93, 24, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (94, 24, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (95, 24, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (96, 24, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (97, 25, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (98, 25, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (99, 25, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (100, 25, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (101, 26, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (102, 26, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (103, 26, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (104, 26, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (105, 27, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (106, 27, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (107, 27, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (108, 27, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (109, 28, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (110, 28, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (111, 28, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (112, 28, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (113, 29, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (114, 29, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (115, 29, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (116, 29, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (117, 30, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (118, 30, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (119, 30, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (120, 30, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (121, 31, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (122, 31, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (123, 31, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (124, 31, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (125, 32, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (126, 32, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (127, 32, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (128, 32, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (129, 33, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (130, 33, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (131, 33, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (132, 33, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (133, 34, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (134, 34, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (135, 34, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (136, 34, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (137, 35, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (138, 35, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (139, 35, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (140, 35, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (141, 36, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (142, 36, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (143, 36, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (144, 36, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (145, 37, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (146, 37, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (147, 37, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (148, 37, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (149, 38, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (150, 38, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (151, 38, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (152, 38, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (153, 39, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (154, 39, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (155, 39, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (156, 39, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (157, 40, 'A', '从不', 1, 1);
INSERT INTO `psy_option` VALUES (158, 40, 'B', '偶尔', 2, 2);
INSERT INTO `psy_option` VALUES (159, 40, 'C', '经常', 3, 3);
INSERT INTO `psy_option` VALUES (160, 40, 'D', '总是', 5, 4);
INSERT INTO `psy_option` VALUES (161, 41, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (162, 41, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (163, 41, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (164, 41, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (165, 42, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (166, 42, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (167, 42, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (168, 42, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (169, 43, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (170, 43, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (171, 43, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (172, 43, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (173, 44, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (174, 44, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (175, 44, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (176, 44, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (177, 45, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (178, 45, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (179, 45, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (180, 45, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (181, 46, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (182, 46, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (183, 46, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (184, 46, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (185, 47, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (186, 47, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (187, 47, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (188, 47, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (189, 48, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (190, 48, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (191, 48, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (192, 48, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (193, 49, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (194, 49, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (195, 49, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (196, 49, 'D', '非常不同意', 5, 4);
INSERT INTO `psy_option` VALUES (197, 50, 'A', '非常同意', 1, 1);
INSERT INTO `psy_option` VALUES (198, 50, 'B', '同意', 2, 2);
INSERT INTO `psy_option` VALUES (199, 50, 'C', '不同意', 3, 3);
INSERT INTO `psy_option` VALUES (200, 50, 'D', '非常不同意', 5, 4);

-- ----------------------------
-- Table structure for psy_publicity
-- ----------------------------
DROP TABLE IF EXISTS `psy_publicity`;
CREATE TABLE `psy_publicity`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
  `summary` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '摘要',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `view_count` int(11) NULL DEFAULT 0 COMMENT '浏览量',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0下架/1上架)',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '心理宣传表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_publicity
-- ----------------------------
INSERT INTO `psy_publicity` VALUES (1, '世界精神卫生日宣传', '/uploads/2026/02/06/760e87661cfd46929f2c41c0386e761f.jpg', '每年10月10日是世界精神卫生日，让我们一起关注心理健康。', '<h2>世界精神卫生日</h2><p>世界精神卫生日是由世界心理卫生联合会于1992年发起的，定于每年的10月10日。</p><p>设立这个日子的目的是为了提高公众对精神卫生问题的认识，促进精神疾病的预防和康复。</p><p>让我们一起关注心理健康，消除对精神疾病的歧视和偏见！</p>', 125, 1, 1, '2026-02-03 00:00:00', '2026-02-07 13:38:37');
INSERT INTO `psy_publicity` VALUES (2, '525心理健康日活动宣传', '/uploads/2026/02/06/ed1b47ef390a40fcad60b6d6f1a915ed.jpg', '5月25日是全国心理健康日，谐音\"我爱我\"，关爱自己从心开始。', '<h2>525心理健康日</h2><p>5月25日是全国心理健康日，谐音\"我爱我\"。</p><p>这一天提醒我们要关爱自己，关注心理健康。</p><p>活动内容：心理讲座、心理测试、团体辅导等。</p>', 101, 1, 2, '2026-02-03 00:00:00', '2026-02-07 17:22:12');
INSERT INTO `psy_publicity` VALUES (3, '心理健康知识进校园', '/uploads/2026/02/06/c92b9d0a636c40ab81bf89eb66df99d7.jpg', '普及心理健康知识，关爱青少年成长。', '<h2>心理健康知识进校园</h2><p>为了帮助青少年更好地了解心理健康知识，我们开展\"心理健康知识进校园\"系列活动。</p><p>活动将在各学校开展心理健康讲座、心理咨询等活动。</p>', 87, 1, 3, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_publicity` VALUES (4, '阳光心理，快乐成长', '/uploads/2026/02/06/d7e20bf4db394fe0a0c474b928d7afcc.jpg', '培养积极心态，做阳光少年。', '<h2>阳光心理，快乐成长</h2><p>阳光的心态是健康成长的基础。</p><p>让我们一起培养积极乐观的心态，面对生活中的挑战，做一个阳光少年！</p>', 145, 1, 4, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_publicity` VALUES (5, '心理健康从我做起', '/uploads/2026/02/06/6422fc8274c24739818030cf91f772fa.jpg', '关注心理健康，从日常小事做起。', '<h2>心理健康从我做起</h2><p>心理健康不是遥不可及的目标，而是需要我们在日常生活中不断努力。</p><p>从保持良好的作息、积极的心态、和谐的人际关系做起，每个人都可以拥有健康的心理！</p>', 113, 1, 5, '2026-02-03 00:00:00', '2026-02-07 17:39:47');

-- ----------------------------
-- Table structure for psy_question
-- ----------------------------
DROP TABLE IF EXISTS `psy_question`;
CREATE TABLE `psy_question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试卷ID',
  `title` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '题目内容',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_test_id`(`test_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_question
-- ----------------------------
INSERT INTO `psy_question` VALUES (1, 1, '最近一周，您是否感到头痛？', 1, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (2, 1, '最近一周，您是否感到紧张或不安？', 2, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (3, 1, '最近一周，您是否有不愉快的想法或念头？', 3, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (4, 1, '最近一周，您是否感到容易哭泣？', 4, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (5, 1, '最近一周，您是否难以入睡？', 5, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (6, 1, '最近一周，您是否感到孤独？', 6, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (7, 1, '最近一周，您是否感到做任何事情都很困难？', 7, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (8, 1, '最近一周，您是否感到前途没有希望？', 8, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (9, 1, '最近一周，您是否感到自己没有价值？', 9, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (10, 1, '最近一周，您是否感到容易发怒？', 10, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (11, 2, '我感到紧张或心神不定', 1, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (12, 2, '我感到害怕，好像有可怕的事情要发生', 2, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (13, 2, '我心里充满担忧', 3, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (14, 2, '我能够放松下来，感到舒适', 4, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (15, 2, '我感到害怕，好像胃里有东西在翻腾', 5, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (16, 2, '我感到坐立不安', 6, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (17, 2, '我会突然感到恐慌', 7, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (18, 2, '我感到心跳加速', 8, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (19, 2, '我感到呼吸困难', 9, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (20, 2, '我感到手脚发抖', 10, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (21, 3, '我感到心情沮丧', 1, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (22, 3, '我对以前喜欢的事情失去了兴趣', 2, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (23, 3, '我感到疲倦或没有精力', 3, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (24, 3, '我的食欲下降或增加', 4, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (25, 3, '我难以集中注意力', 5, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (26, 3, '我感到自己是失败者', 6, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (27, 3, '我对自己的未来感到悲观', 7, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (28, 3, '我有想伤害自己的念头', 8, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (29, 3, '我感到自己不如别人', 9, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (30, 3, '我对自己感到失望', 10, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (31, 4, '我经常因为学业而感到压力很大', 1, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (32, 4, '考试前我会感到非常焦虑', 2, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (33, 4, '我担心自己的成绩不够好', 3, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (34, 4, '我觉得学习任务太重了', 4, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (35, 4, '我因为学习而睡眠不好', 5, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (36, 4, '我感到学习压力影响了我的身体健康', 6, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (37, 4, '我害怕让父母失望', 7, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (38, 4, '我经常与其他同学比较成绩', 8, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (39, 4, '我觉得时间总是不够用', 9, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (40, 4, '我对未来升学感到担忧', 10, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (41, 5, '我能够轻松地与陌生人交谈', 1, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (42, 5, '我在人多的场合感到自在', 2, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (43, 5, '我能够很好地表达自己的想法', 3, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (44, 5, '我喜欢参加集体活动', 4, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (45, 5, '我能够妥善处理与他人的冲突', 5, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (46, 5, '我有很多好朋友', 6, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (47, 5, '我能够理解他人的感受', 7, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (48, 5, '我在团队中能够很好地合作', 8, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (49, 5, '我能够接受他人的批评', 9, '2026-02-03 00:00:00');
INSERT INTO `psy_question` VALUES (50, 5, '我乐于帮助他人', 10, '2026-02-03 00:00:00');

-- ----------------------------
-- Table structure for psy_registration
-- ----------------------------
DROP TABLE IF EXISTS `psy_registration`;
CREATE TABLE `psy_registration`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态(0待审核/1已通过/2已拒绝/3已取消)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '活动报名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_registration
-- ----------------------------
INSERT INTO `psy_registration` VALUES (1, 1, 1, 1, '想学习心理健康知识', '2026-02-03 10:00:00', '审核通过', '2026-02-03 08:00:00');
INSERT INTO `psy_registration` VALUES (2, 2, 1, 1, '希望能参加活动', '2026-02-03 10:00:00', '审核通过', '2026-02-03 08:30:00');
INSERT INTO `psy_registration` VALUES (3, 3, 2, 1, '想参加团体辅导', '2026-02-03 11:00:00', '审核通过', '2026-02-03 09:00:00');
INSERT INTO `psy_registration` VALUES (4, 4, 2, 0, '报名参加', NULL, NULL, '2026-02-03 09:30:00');
INSERT INTO `psy_registration` VALUES (5, 5, 3, 1, '喜欢这部电影', '2026-02-03 14:00:00', '审核通过', '2026-02-03 10:00:00');
INSERT INTO `psy_registration` VALUES (6, 6, 3, 2, '', '2026-02-03 14:00:00', '名额已满', '2026-02-03 10:30:00');
INSERT INTO `psy_registration` VALUES (7, 7, 4, 1, '想学习压力管理', '2026-02-03 15:00:00', '审核通过', '2026-02-03 11:00:00');
INSERT INTO `psy_registration` VALUES (8, 8, 5, 0, '和家长一起参加', NULL, NULL, '2026-02-03 11:30:00');
INSERT INTO `psy_registration` VALUES (9, 1, 3, 1, '想看心理电影', '2026-02-03 16:00:00', '审核通过', '2026-02-03 12:00:00');
INSERT INTO `psy_registration` VALUES (10, 2, 4, 1, '学习减压方法', '2026-02-03 16:00:00', '审核通过', '2026-02-03 12:30:00');
INSERT INTO `psy_registration` VALUES (11, 11, 1, 1, '666', '2026-02-07 15:15:44', '同意', '2026-02-07 13:47:59');

-- ----------------------------
-- Table structure for psy_test
-- ----------------------------
DROP TABLE IF EXISTS `psy_test`;
CREATE TABLE `psy_test`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '测试标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '测试描述',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面图',
  `total_score` int(11) NULL DEFAULT 100 COMMENT '总分',
  `health_threshold` int(11) NULL DEFAULT 60 COMMENT '健康阈值(低于此分数为健康)',
  `good_threshold` int(11) NULL DEFAULT 80 COMMENT '良好阈值(高于等于此分数为建议沟通)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0下架/1上架)',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试卷表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_test
-- ----------------------------
INSERT INTO `psy_test` VALUES (1, 'SCL-90心理健康自评量表', 'SCL-90是世界上最著名的心理健康测试量表之一，用于评估个体的心理健康水平。本测试包含多个维度的心理健康评估，结果仅供参考。', '/uploads/2026/02/06/5b453a8011954ae5aaa3208794511e12.jpg', 100, 60, 80, 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_test` VALUES (2, '青少年焦虑自评量表', '本量表用于评估青少年的焦虑水平，帮助了解自己的心理状态。测试结果仅供参考，如有需要请寻求专业帮助。', '/uploads/2026/02/06/32395587d7fe4db381d5dd06b80d4ccc.jpg', 100, 60, 80, 1, 2, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_test` VALUES (3, '青少年抑郁自评量表', '本量表用于评估青少年的抑郁水平，及时发现潜在的心理问题。测试结果仅供参考，如有需要请寻求专业帮助。', '/uploads/2026/02/06/9965d66e8f464df0850563c41c603d07.jpg', 100, 60, 80, 1, 3, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_test` VALUES (4, '学习压力评估量表', '本量表用于评估学生的学习压力水平，帮助学生了解自己的压力状况并采取相应措施。', '/uploads/2026/02/06/5da31779591948a68ba97dfb96c07fef.jpg', 100, 60, 80, 1, 4, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `psy_test` VALUES (5, '人际关系能力测评', '本量表用于评估个人的人际交往能力，帮助了解自己在人际交往方面的优势和不足。', '/uploads/2026/02/06/04279eec32934843af320d71aa2b0e6a.jpg', 100, 60, 80, 1, 5, '2026-02-03 00:00:00', '2026-02-06 16:38:03');

-- ----------------------------
-- Table structure for psy_test_record
-- ----------------------------
DROP TABLE IF EXISTS `psy_test_record`;
CREATE TABLE `psy_test_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `test_id` bigint(20) NOT NULL COMMENT '测试卷ID',
  `total_score` int(11) NULL DEFAULT 0 COMMENT '得分',
  `result` tinyint(4) NULL DEFAULT 0 COMMENT '结果(0健康/1良好/2建议沟通)',
  `answer_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '答题详情(JSON)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '测试时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_test_id`(`test_id`) USING BTREE,
  INDEX `idx_user_test`(`user_id`, `test_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '测试记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of psy_test_record
-- ----------------------------
INSERT INTO `psy_test_record` VALUES (1, 1, 1, 45, 0, '[{\"questionId\":1,\"optionId\":1},{\"questionId\":2,\"optionId\":2},{\"questionId\":3,\"optionId\":1}]', '2026-02-01 10:30:00');
INSERT INTO `psy_test_record` VALUES (2, 2, 1, 68, 1, '[{\"questionId\":1,\"optionId\":3},{\"questionId\":2,\"optionId\":3},{\"questionId\":3,\"optionId\":2}]', '2026-02-01 11:00:00');
INSERT INTO `psy_test_record` VALUES (3, 3, 1, 85, 2, '[{\"questionId\":1,\"optionId\":4},{\"questionId\":2,\"optionId\":4},{\"questionId\":3,\"optionId\":3}]', '2026-02-01 14:30:00');
INSERT INTO `psy_test_record` VALUES (4, 4, 2, 52, 0, '[{\"questionId\":11,\"optionId\":41},{\"questionId\":12,\"optionId\":42}]', '2026-02-02 09:00:00');
INSERT INTO `psy_test_record` VALUES (5, 5, 2, 35, 0, '[{\"questionId\":11,\"optionId\":41},{\"questionId\":12,\"optionId\":41}]', '2026-02-02 10:30:00');
INSERT INTO `psy_test_record` VALUES (6, 6, 3, 72, 1, '[{\"questionId\":21,\"optionId\":81},{\"questionId\":22,\"optionId\":82}]', '2026-02-02 14:00:00');
INSERT INTO `psy_test_record` VALUES (7, 7, 3, 48, 0, '[{\"questionId\":21,\"optionId\":81},{\"questionId\":22,\"optionId\":81}]', '2026-02-02 15:30:00');
INSERT INTO `psy_test_record` VALUES (8, 8, 4, 88, 2, '[{\"questionId\":31,\"optionId\":124},{\"questionId\":32,\"optionId\":128}]', '2026-02-02 16:00:00');
INSERT INTO `psy_test_record` VALUES (9, 1, 2, 55, 0, '[{\"questionId\":11,\"optionId\":42},{\"questionId\":12,\"optionId\":42}]', '2026-02-03 08:00:00');
INSERT INTO `psy_test_record` VALUES (10, 2, 3, 62, 1, '[{\"questionId\":21,\"optionId\":82},{\"questionId\":22,\"optionId\":82}]', '2026-02-03 09:30:00');
INSERT INTO `psy_test_record` VALUES (11, 11, 1, 17, 0, '[{\"questionId\":1,\"optionId\":1},{\"questionId\":2,\"optionId\":6},{\"questionId\":3,\"optionId\":10},{\"questionId\":4,\"optionId\":13},{\"questionId\":5,\"optionId\":18},{\"questionId\":6,\"optionId\":22},{\"questionId\":7,\"optionId\":26},{\"questionId\":8,\"optionId\":29},{\"questionId\":9,\"optionId\":34},{\"questionId\":10,\"optionId\":38}]', '2026-02-07 15:04:52');
INSERT INTO `psy_test_record` VALUES (12, 11, 2, 12, 0, '[{\"questionId\":11,\"optionId\":41},{\"questionId\":12,\"optionId\":45},{\"questionId\":13,\"optionId\":49},{\"questionId\":14,\"optionId\":53},{\"questionId\":15,\"optionId\":57},{\"questionId\":16,\"optionId\":62},{\"questionId\":17,\"optionId\":65},{\"questionId\":18,\"optionId\":70},{\"questionId\":19,\"optionId\":73},{\"questionId\":20,\"optionId\":77}]', '2026-02-07 15:14:33');

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码(加密)',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0禁用/1启用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', '/uploads/2026/02/06/78b9235f63664da6bf9f6518fee90b75.png', '13800138000', 1, '2026-02-03 00:00:00', '2026-02-06 20:20:26');
INSERT INTO `sys_admin` VALUES (2, 'manager', 'e10adc3949ba59abbe56e057f20f883e', '运营管理员', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', '13800138001', 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');

-- ----------------------------
-- Table structure for sys_banner
-- ----------------------------
DROP TABLE IF EXISTS `sys_banner`;
CREATE TABLE `sys_banner`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片地址',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0禁用/1启用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_banner
-- ----------------------------
INSERT INTO `sys_banner` VALUES (1, '关注青少年心理健康', '/uploads/2026/02/06/aa1306090c0343ebbc200fb50ea8af5c.jpg', '/front/knowledge/1', 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_banner` VALUES (2, '心理测试上线啦', '/uploads/2026/02/06/5ee5fff428534d7ea1c1600f6f95bc0d.jpg', '/front/test', 2, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_banner` VALUES (3, '心理健康活动招募中', '/uploads/2026/02/06/7ed0f3c6eb034900896002bea0cc0a31.jpg', '/front/activity/1', 3, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_banner` VALUES (4, 'AI心理助手24小时在线', '/uploads/2026/02/06/6e49ee31775f489a89218ae557cda38d.jpg', '/front/ai-chat', 4, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_banner` VALUES (5, '每日心理小贴士', '/uploads/2026/02/06/70229d614a044d5aa6808ff82794106d.jpg', '/front/news/1', 5, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '公告内容',
  `type` tinyint(4) NULL DEFAULT 1 COMMENT '类型(1通知/2公告)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0禁用/1启用)',
  `is_top` tinyint(4) NULL DEFAULT 0 COMMENT '是否置顶(0否/1是)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '欢迎使用青少年心理健康管理系统', '<p>亲爱的同学们，欢迎来到青少年心理健康管理系统！</p><p>本系统致力于为青少年提供专业的心理健康服务，包括心理科普、心理测试、心理咨询等功能。</p><p>如果你在学习或生活中遇到任何困扰，都可以通过本系统获得帮助。</p><p>祝大家身心健康，快乐成长！</p>', 2, 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_notice` VALUES (2, '心理测试功能上线通知', '<p>各位用户：</p><p>我们的心理测试功能现已正式上线！</p><p>您可以通过心理测试了解自己的心理健康状况，测试结果仅供参考，如有需要请寻求专业帮助。</p>', 1, 1, 0, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_notice` VALUES (3, '春季心理健康周活动通知', '<p>为了促进青少年心理健康发展，我们将在3月举办\"春季心理健康周\"活动。</p><p>活动包括：心理讲座、团体辅导、心理电影赏析等。</p><p>欢迎大家踊跃参与！</p>', 1, 1, 0, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_notice` VALUES (4, '系统维护通知', '<p>尊敬的用户：</p><p>系统将于2026年2月10日凌晨2:00-4:00进行维护升级，届时系统将暂停服务。</p><p>给您带来不便，敬请谅解！</p>', 1, 1, 0, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_notice` VALUES (5, '关于建立心理健康档案的通知', '<p>为更好地跟踪和帮助同学们的心理健康发展，我们将为每位用户建立心理健康档案。</p><p>档案内容包括：测试记录、咨询记录等，所有信息将严格保密。</p>', 2, 1, 0, '2026-02-03 00:00:00', '2026-02-06 16:38:03');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码(加密)',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(4) NULL DEFAULT 1 COMMENT '性别(0女/1男)',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `health_status` tinyint(4) NULL DEFAULT 0 COMMENT '健康状态(0健康/1良好/2建议沟通)',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态(0禁用/1启用)',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 16, '13900139001', 'zhangsan@example.com', 0, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (2, 'lisi', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 15, '13900139002', 'lisi@example.com', 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (3, 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '王五', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 17, '13900139003', 'wangwu@example.com', 2, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (4, 'zhaoliu', 'e10adc3949ba59abbe56e057f20f883e', '赵六', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 0, 14, '13900139004', 'zhaoliu@example.com', 0, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (5, 'xiaoming', 'e10adc3949ba59abbe56e057f20f883e', '小明', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 16, '13900139005', 'xiaoming@example.com', 0, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (6, 'xiaohong', 'e10adc3949ba59abbe56e057f20f883e', '小红', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 0, 15, '13900139006', 'xiaohong@example.com', 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (7, 'xiaofang', 'e10adc3949ba59abbe56e057f20f883e', '小芳', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 0, 17, '13900139007', 'xiaofang@example.com', 0, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (8, 'xiaoqiang', 'e10adc3949ba59abbe56e057f20f883e', '小强', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 18, '13900139008', 'xiaoqiang@example.com', 2, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (9, 'xiaohua', 'e10adc3949ba59abbe56e057f20f883e', '小华', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 1, 14, '13900139009', 'xiaohua@example.com', 0, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (10, 'xiaoli', 'e10adc3949ba59abbe56e057f20f883e', '小丽', 'https://tse2.mm.bing.net/th/id/OIP.BD147BaQ45d_Ixe1eV5aNAHaJ1?rs=1&pid=ImgDetMain&o=7&rm=3', 0, 16, '13900139010', 'xiaoli@example.com', 1, 1, '2026-02-03 00:00:00', '2026-02-06 16:38:03');
INSERT INTO `sys_user` VALUES (11, 'yyf', 'e10adc3949ba59abbe56e057f20f883e', '碳烤小肥羊', '/uploads/2026/02/06/e2ea5dc195a949089c811e562caf4f16.jpg', 1, 22, '16666666666', '123@qq.com', 0, 1, '2026-02-06 17:11:17', '2026-02-07 15:14:33');

SET FOREIGN_KEY_CHECKS = 1;
