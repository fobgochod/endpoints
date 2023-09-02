package com.fobgochod.endpoints.view.tree

/**
 * Represents an instance which can be shown in the IDE (e.g. a file, a specific location inside a file, etc).
 *
 * @author fobgochod
 * @date 2023/7/23 13:28
 * @see  com.intellij.pom.Navigatable
 */
interface Navigatable {

    /**
     * Open editor and select/navigate to the object there if possible.
     * Just do nothing if navigation is not possible like in case of a package
     *
     * @param requestFocus `true` if focus requesting is necessary
     */
    fun navigate(requestFocus: Boolean) {}

    /**
     * Indicates whether this instance supports navigation of any kind.
     * Usually this method is called to ensure that navigation is possible.
     * We assume that this method should return `true` in such case,
     * so implement this method respectively.
     *
     * @return `false` if navigation is not possible for any reason.
     */
    fun canNavigate(): Boolean {
        return false
    }
}
