package com.mark.community.service;

import com.mark.community.dto.FileResponse;
import com.mark.community.entity.UploadFile;
import com.mark.community.enums.FileType;
import com.mark.community.exception.CustomException;
import com.mark.community.repository.UploadFileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private FileService fileService;


    private MultipartFile createSafeMockFile(String fileName, String contentType) {
        return new MockMultipartFile("file", fileName, contentType, "data".getBytes()) {
            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
    }

    @Test
    void 파일업로드시_정상적으로_저장된다(){
        MultipartFile file = createSafeMockFile("photo.png", "image/png");
        when(uploadFileRepository.save(any(UploadFile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UploadFile result = fileService.upload(file);

        assertNotNull(result);
        assertEquals("photo.png", result.getFileName());
        assertEquals(FileType.IMAGE, result.getFileType());
    }

    @Test
    void 파일조회시_파일_조회에_실패하면_예외를_던진다(){
        when(uploadFileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> fileService.getFile(1L));
    }

    @Test
    void 파일조회시_정상적으로_조회된다(){
        UploadFile uploadFile = new UploadFile("photo.png", "/path", FileType.IMAGE, 100L);
        when(uploadFileRepository.findById(1L)).thenReturn(Optional.of(uploadFile));

        FileResponse response = fileService.getFile(1L);

        assertEquals("photo.png", response.getFileName());
        assertEquals("/path", response.getFilePath());
    }

    @Test
    void 파일목록조회시_여러개가_정상적으로_조회된다(){
        UploadFile file1 = new UploadFile("photo1.png", "/path1", FileType.IMAGE, 100L);
        UploadFile file2 = new UploadFile("photo2.png", "/path2", FileType.IMAGE, 200L);
        when(uploadFileRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(file1, file2));

        List<FileResponse> responses = fileService.getFiles(List.of(1L, 2L));

        assertEquals(2, responses.size());
    }
}
