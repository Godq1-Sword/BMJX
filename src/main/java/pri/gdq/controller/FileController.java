package pri.gdq.controller;

import pri.gdq.entity.ResponseBody;
import pri.gdq.util.ConfigUtil;
import pri.gdq.util.DateUtil;
import pri.gdq.util.ResponseBodyUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * program : taxoa-backend
 * description: 文件Controller
 *
 * @author : gdq
 * data : 2019-12-09 14:20
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * description : 上传文件
     *
     * @param file:文件
     * @return ResponseBody
     * @author : gdq
     * data : 2020/1/3 0003 17:47
     */
    @PostMapping
    public ResponseBody addFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = ConfigUtil.FILE_PATH + DateUtil.getFilePathByDate(System.currentTimeMillis());
        File localFile = new File(filePath + fileName);
        File imageMkdirs = new File(filePath);
        if (!imageMkdirs.exists()) {
            boolean bool = imageMkdirs.mkdirs();
            if (!bool) {
                return ResponseBodyUtil.generateErrResp("上传文件失败,无法在路径" + filePath + "下创建文件夹");
            }
        }
        file.transferTo(localFile);
        return ResponseBodyUtil.generateSucResp(localFile, "上传文件成功");
    }

    /**
     * description : 下载文件
     *
     * @param request:http请求体
     * @param response:http响应体
     * @author : gdq
     * data : 2020/1/3 0003 17:47
     */
    @GetMapping
    public void getFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");
        response.setContentType("application/octet-stream;charset=utf-8\"");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        File file = new File(filePath);
        response.setContentLength((int) file.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[128];
            int count;
            while ((count = in.read(buffer)) > 0) {
                response.getOutputStream().write(buffer, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            response.getOutputStream().flush();
            response.getOutputStream().close();
            if (!ObjectUtils.isEmpty(in)) {
                in.close();
            }
        }
    }
}
