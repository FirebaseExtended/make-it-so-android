package com.example.makeitso.model

enum class Priority {
    None,
    Low,
    Medium,
    High;

    companion object {
        fun getByName(name: String?): Priority {
            values().forEach { priority ->
                if (name == priority.name) return priority
            }

            return None
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { priority -> options.add(priority.name) }
            return options
        }
    }
}
