package com.feicui.apphx.presentation.contact.search;


import android.content.SearchRecentSuggestionsProvider;

/**
 * 根据最近查询/浏览，提供简单的搜索建议
 */
public class HxSearchContactProvider extends SearchRecentSuggestionsProvider {

    public static final int MODE = DATABASE_MODE_QUERIES;

    public static final String AUTHORITY = "com.feicuiedu.apphx.HxSearchContactProvider";
    // TODO: 2016/10/18 0018 若此处报错，则把feicuiedu修改为自己的包名，并对清单列表及strings.xml中对应的值予以改变

    public HxSearchContactProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
