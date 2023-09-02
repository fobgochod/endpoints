package com.fobgochod.endpoints.domain

data class PackageTree(
    val level: Int,
    val name: String,
    val packageName: String
) {

    val compactedName = packageName.split(".").filterIndexed { index, _ -> index <= level }.joinToString(".")
    val parentName = compactedName.substringBeforeLast(name).dropLast(1)

    companion object {
        fun build(packageName: String): List<PackageTree> {
            return packageName.split(".")
                .mapIndexed { index, name ->
                    PackageTree(index, name, packageName)
                }.toList()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PackageTree

        return compactedName == other.compactedName
    }

    override fun hashCode(): Int {
        return compactedName.hashCode()
    }
}
