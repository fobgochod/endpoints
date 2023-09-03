package com.fobgochod.endpoints.util

object PathUtils {

    private const val SLASH = "/"
    private const val PREFIX = '{'
    private const val SUFFIX = '}'

    /**
     * Examples:
     *
     * null -> /
     * "" -> ""
     * /api/demo/v2/ -> /api/demo/v2
     * /api/demo/v2 -> /api/demo/v2
     * api/demo/v2/ -> /api/demo/v2
     *
     * @param path path
     * @return format path
     */
    fun formatPath(path: String?): String {
        return when (path) {
            null -> SLASH
            "" -> ""
            else -> SLASH + path.removePrefix(SLASH).removeSuffix(SLASH)
        }
    }

    /**
     * replace path variable
     */
    fun replace(target: String, paths: Map<String, String>): String {
        val builder = StringBuilder(target)
        var chars = target.toCharArray()
        var length = chars.size
        var pos = 0
        while (pos < length) {
            if (chars[pos] == PREFIX) {
                val startPos = pos
                while (pos < length) {
                    if (chars[pos] == SUFFIX) {
                        val endPos = pos
                        val pathName = String(chars, startPos + 1, endPos - startPos - 1).trimEnd(':', '.', '+')
                        val pathValue: String = paths.getOrDefault(pathName, "")
                        builder.replace(startPos, endPos + 1, pathValue)
                        val change = pathValue.length - (endPos - startPos + 1)
                        pos += change + 1
                        length += change
                        chars = builder.toString().toCharArray()
                        break
                    } else {
                        pos++
                    }
                }
            } else {
                pos++
            }
        }
        return builder.toString()
    }
}
