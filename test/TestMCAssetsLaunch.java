import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import sk.tomsik68.mclauncher.api.common.ILaunchSettings;
import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.login.IProfile;
import sk.tomsik68.mclauncher.api.login.ISession;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.impl.login.legacy.LegacyLoginService;
import sk.tomsik68.mclauncher.impl.login.legacy.LegacyProfile;
import sk.tomsik68.mclauncher.impl.versions.mcassets.MCAssetsVersion;
import sk.tomsik68.mclauncher.impl.versions.mcassets.MCAssetsVersionList;


public class TestMCAssetsLaunch {

    @Test
    public void test() {
        final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
        MCAssetsVersionList list = new MCAssetsVersionList();
        list.addObserver(new IObserver<IVersion>() {
            private boolean launched = false;
            @Override
            public void onUpdate(IObservable<IVersion> observable, IVersion changed) {
                if(!launched){
                    launched = true;
                    IProfile profile = new LegacyProfile("Tomsik68@gmail.com", "blahblahblah");
                    LegacyLoginService lls = new LegacyLoginService();
                    ISession session = null;
                    try {
                         session = lls.login(profile);
                        System.out.println("Legacy Login: " + session.getSessionID());
                    } catch (Exception e) {
                        e.printStackTrace();
                        fail(e.getMessage());
                    }
                    System.out.println("Found version: "+changed.getDisplayName());
                    try {
                        Process proc = changed.getLauncher().launch(session, mc, null, (MCAssetsVersion) changed, new ILaunchSettings() {
                            
                            @Override
                            public boolean isModifyAppletOptions() {
                                return false;
                            }
                            
                            @Override
                            public boolean isErrorStreamRedirected() {
                                return true;
                            }
                            
                            @Override
                            public String getWorkingDirectory() {
                                return null;
                            }
                            
                            @Override
                            public String getInitHeap() {
                                return "2G";
                            }
                            
                            @Override
                            public String getHeap() {
                                return "3G";
                            }
                            
                            @Override
                            public Map<String, String> getCustomParameters() {
                                return null;
                            }
                            
                            @Override
                            public List<String> getCommandPrefix() {
                                return Collections.emptyList();
                            }
                        });
                        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        String line;
                        while(true){
                            line = br.readLine();
                            if(line != null && line.length() > 0)
                                System.out.println(line);
                        }
                    } catch (Exception e) {
                        fail(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
