package com.utp.gp.inventarioSMP.controlador;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BackupController {

     @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${mysql.dump.path:C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe}")
    private String mysqldumpPath;
    
    private String getDatabaseName() {
        return dbUrl.substring(dbUrl.lastIndexOf("/") + 1);
    }
    
    @GetMapping("/backUp")
    public ResponseEntity<InputStreamResource> generarBackup() throws IOException, InterruptedException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String backupFileName = "backup_" + timeStamp + ".sql";
        String backupPath = System.getProperty("user.home") + File.separator + "backups" + File.separator + backupFileName;
        
        new File(System.getProperty("user.home") + File.separator + "backups").mkdirs();
        
        String dbName = getDatabaseName();
        String command = String.format("cmd /c \"%s\" -u %s -p%s %s -r %s", 
                mysqldumpPath, dbUsername, dbPassword, dbName, backupPath);
        
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        
        File backupFile = new File(backupPath);
        if (!backupFile.exists()) {
            throw new RuntimeException("Error al generar el backup");
        }
        
        InputStreamResource resource = new InputStreamResource(new FileInputStream(backupFile));
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + backupFileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(backupFile.length())
                .body(resource);
    }

}
