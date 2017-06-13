package kr.co.enterprise1.mfpdemo.common;
/**
* Copyright 2016 IBM Corp.
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

public class Constants {
    public static final String ACTION_LOGIN = "kr.co.enterprise1.mfpdemo.broadcast.login";
    public static final String ACTION_LOGIN_SUCCESS = "kr.co.enterprise1.mfpdemo.broadcast.login.success";
    public static final String ACTION_LOGIN_FAILURE = "kr.co.enterprise1.mfpdemo.broadcast.login.failure";
    public static final String ACTION_LOGIN_REQUIRED = "kr.co.enterprise1.mfpdemo.broadcast.login.required";

    public static final String ACTION_LOGOUT = "kr.co.enterprise1.mfpdemo.broadcast.logout";
    public static final String ACTION_LOGOUT_SUCCESS = "kr.co.enterprise1.mfpdemo.broadcast.logout.success";
    public static final String ACTION_LOGOUT_FAILURE = "kr.co.enterprise1.mfpdemo.broadcast.logout.failure";

    public static final String PREFERENCES_FILE = "kr.co.enterprise1.mfpdemo.preferences";
    public static final String PREFERENCES_KEY_USER = "kr.co.enterprise1.mfpdemo.preferences.user";
}