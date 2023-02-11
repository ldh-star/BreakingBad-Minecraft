package org.liangguo.breakingbad.utils


/**
 * @author ldh
 * 时间: 2023/1/27 13:21
 * 邮箱: ldh.liangguo@outlook.com
 */
/**
 * 对于任何实例，试着把他们转换为另一种类的实例并在函数块中执行相应的操作。
 */
inline fun <reified T> Any?.tryAs(func: (T) -> Unit) {
    if (this is T) {
        func(this)
    }
}



