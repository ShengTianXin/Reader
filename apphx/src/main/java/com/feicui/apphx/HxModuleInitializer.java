package com.feicui.apphx;


import com.feicui.apphx.model.HxContactManager;
import com.feicui.apphx.model.repository.ILocalUsersRepo;
import com.feicui.apphx.model.repository.IRemoteUserRepo;

/**
 * AppHx模块初始化工具
 */

public class HxModuleInitializer {

    private static HxModuleInitializer hxModuleInitializer;

    public static HxModuleInitializer getInstance() {
        if (hxModuleInitializer == null) {
            hxModuleInitializer = new HxModuleInitializer();
        }
        return hxModuleInitializer;
    }

    public void init(
            IRemoteUserRepo remoteUsersRepo,
            ILocalUsersRepo localUsersRepo){

        // 初始化联系操作内的本地及远程用户仓库
        HxContactManager.getInstance()
                .initLocalUsersRepo(localUsersRepo)
                .initRemoteUserRepo(remoteUsersRepo);

    }
}
