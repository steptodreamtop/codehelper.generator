package main.java.com.ccnode.codegenerator.genCode;

import main.java.com.ccnode.codegenerator.pojo.GenCodeRequest;
import main.java.com.ccnode.codegenerator.pojo.GenCodeResponse;
import main.java.com.ccnode.codegenerator.pojo.OnePojoInfo;
import main.java.com.ccnode.codegenerator.pojoHelper.OnePojoInfoHelper;
import main.java.com.ccnode.codegenerator.util.LoggerWrapper;
import org.slf4j.Logger;

import java.io.File;

/**
 * What always stop you is what you always believe.
 * <p>
 * Created by zhengjun.du on 2016/05/17 19:52
 */
public class GenCodeService {

    private final static Logger LOGGER = LoggerWrapper.getLogger(GenCodeService.class);

    public static GenCodeResponse genCode(GenCodeRequest request){
        GenCodeResponse response = new GenCodeResponse();
        try{

            response.setUserConfigMap(UserConfigService.userConfigMap);
//            response = UserConfigService.readConfigFile(request.getProjectPath());
            LOGGER.info("UserConfigService.readConfigFile done");
            if(response.checkFailure()){
                return response;
            }

            response.setRequest(request);
            if(response.checkFailure()){
                return response;
            }
            response.setPathSplitter(request.getPathSplitter());
            if(response.checkFailure()){
                return response;
            }

            response = UserConfigService.initConfig(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.initConfig done");

            response = InitialService.initPojos(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.initPojos done");

            GenSqlService.genSQL(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.genSQL done");
            GenDaoService.genDAO(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.genDao done");
            GenServiceService.genService(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.genService done");
            GenMapperService.genMapper(response);
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.genMapper done");
            for (OnePojoInfo onePojoInfo : response.getPojoInfos()) {
                OnePojoInfoHelper.flushFiles(onePojoInfo,response);
            }
            if(response.checkFailure()){
                return response;
            }
            LOGGER.info("UserConfigService.flushFiles done");
            response.success();

        }catch(Exception e){
            LOGGER.error("gen code failure ",e);
            response.failure("gen code failure ",e);
        }
        return response;
    }

    public static void main(String[] args) {
        String s = System.getProperty("file.separator");
        File f = new File("/Users/zhengjun/Workspaces/horizon");
        s = f.getParentFile().getAbsolutePath();
        System.out.println(s);
    }
}