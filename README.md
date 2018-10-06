# FilemanagementAPI
API to search,upload,download files from drive 

### Resources:
    End Point: /v1/

####1. Get All Files in Parent Folder
#####Path: files/allFilesInFolder/{parentFolderId}
    parentFolderId = FolderId OR "root"
####2. upload file to Parent Folder
#####Path: files/uploadFileToFolder/{parentFolderId}
    parentFolderId = FolderId OR "root"
####3. download file using FileId
#####Path:files/downloadFileById/{fileId}
    fileId = uniqe FileID

######File Storage: Google Drive