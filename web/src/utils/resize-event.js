import ResizeObserver from 'resize-observer-polyfill'

const resizeHandler = function (entries) {
    for (let entry of entries) {
        let visible = !!(entry.contentRect.height && entry.contentRect.width)
        const listeners = entry.target.__resizeListeners__ || []
        if (listeners.length) {
            listeners.forEach(fn => {
                fn({ visible })
            })
        }
    }
}

export const addResizeListener = function (element, fn) {
    if (!element.__resizeListeners__) {
        element.__resizeListeners__ = []
        element.__ro__ = new ResizeObserver(resizeHandler)
        element.__ro__.observe(element)
    }
    element.__resizeListeners__.push(fn)
}

export const removeResizeListener = function (element, fn) {
    if (!element || !element.__resizeListeners__) return
    element.__resizeListeners__.splice(element.__resizeListeners__.indexOf(fn), 1)
    if (!element.__resizeListeners__.length) {
        element.__ro__.disconnect()
    }
}