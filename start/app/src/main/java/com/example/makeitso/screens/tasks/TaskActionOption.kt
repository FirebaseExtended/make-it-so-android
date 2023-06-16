/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.tasks

enum class TaskActionOption(val title: String) {
  EditTask("Edit task"),
  ToggleFlag("Toggle flag"),
  DeleteTask("Delete task");

  companion object {
    fun getByTitle(title: String): TaskActionOption {
      values().forEach { action -> if (title == action.title) return action }

      return EditTask
    }

    fun getOptions(hasEditOption: Boolean): List<String> {
      val options = mutableListOf<String>()
      values().forEach { taskAction ->
        if (hasEditOption || taskAction != EditTask) {
          options.add(taskAction.title)
        }
      }
      return options
    }
  }
}
