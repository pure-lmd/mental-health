// SVG图标自动注册
// 使用 vite-plugin-svg-icons 或手动注册

const svgModules = import.meta.glob('@/assets/icons/svg/*.svg', { eager: true, query: '?raw', import: 'default' })

const svgIcons = {}

for (const path in svgModules) {
  const name = path.match(/\/([^/]+)\.svg$/)?.[1]
  if (name) {
    svgIcons[name] = svgModules[path]
  }
}

// 创建SVG Sprite
function createSvgSprite() {
  const svgSprite = document.createElement('div')
  svgSprite.style.cssText = 'position: absolute; width: 0; height: 0; overflow: hidden;'
  
  let symbols = ''
  for (const [name, content] of Object.entries(svgIcons)) {
    // 提取 svg 内容并转换为 symbol
    const svgContent = content
      .replace(/<svg[^>]*>/, '')
      .replace(/<\/svg>/, '')
    
    const viewBox = content.match(/viewBox="([^"]+)"/)?.[1] || '0 0 24 24'
    const fill = content.match(/\bfill="([^"]+)"/)?.[1] || ''
    const stroke = content.match(/\bstroke="([^"]+)"/)?.[1] || ''
    const strokeWidth = content.match(/stroke-width="([^"]+)"/)?.[1] || ''
    const strokeLinecap = content.match(/stroke-linecap="([^"]+)"/)?.[1] || ''
    const strokeLinejoin = content.match(/stroke-linejoin="([^"]+)"/)?.[1] || ''
    
    let attrs = `id="icon-${name}" viewBox="${viewBox}"`
    if (fill) attrs += ` fill="${fill}"`
    if (stroke) attrs += ` stroke="${stroke}"`
    if (strokeWidth) attrs += ` stroke-width="${strokeWidth}"`
    if (strokeLinecap) attrs += ` stroke-linecap="${strokeLinecap}"`
    if (strokeLinejoin) attrs += ` stroke-linejoin="${strokeLinejoin}"`
    
    symbols += `<symbol ${attrs}>${svgContent}</symbol>`
  }
  
  svgSprite.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg">${symbols}</svg>`
  
  if (document.body.firstChild) {
    document.body.insertBefore(svgSprite, document.body.firstChild)
  } else {
    document.body.appendChild(svgSprite)
  }
}

export function setupSvgIcons() {
  createSvgSprite()
}

export default svgIcons
