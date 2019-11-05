/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.riotx.features.settings.ignored

import com.airbnb.epoxy.EpoxyController
import im.vector.riotx.R
import im.vector.riotx.core.epoxy.noResultItem
import im.vector.riotx.core.resources.StringProvider
import javax.inject.Inject

class IgnoredUsersController @Inject constructor(private val stringProvider: StringProvider) : EpoxyController() {

    var callback: Callback? = null
    private var viewState: IgnoredUsersViewState? = null

    init {
        requestModelBuild()
    }

    fun update(viewState: IgnoredUsersViewState) {
        this.viewState = viewState
        requestModelBuild()
    }

    override fun buildModels() {
        val nonNullViewState = viewState ?: return
        buildIgnoredUserModels(nonNullViewState.ignoredUserIds)
    }

    private fun buildIgnoredUserModels(userIds: List<String>) {
        if (userIds.isEmpty()) {
            noResultItem {
                id("empty")
                text(stringProvider.getString(R.string.no_ignored_users))
            }
        } else {
            userIds.forEach { userId ->
                ignoredUserItem {
                    id(userId)
                    userId(userId)
                    itemClickAction { callback?.onUserIdClicked(userId) }
                }
            }
        }
    }

    interface Callback {
        fun onUserIdClicked(userId: String)
    }
}
