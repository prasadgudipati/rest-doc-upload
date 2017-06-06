package org.finratest.archive.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.finratest.archive.dao.IDocumentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("archiveService")
public class ArchiveService implements IArchiveService, Serializable {

    private static final long serialVersionUID = 8119784722798361327L;
    
    @Autowired
    private IDocumentDao DocumentDao;

    /**
     * Saves a document in the archive.
     * @see org.finratest.archive.service.IArchiveService#save(org.finratest.archive.service.Document)
     */
    @Override
    public DocumentMetadata save(Document document) {
        getDocumentDao().insert(document); 
        return document.getMetadata();
    }
    
    /**
     * Finds document in the archive
     * @see org.finratest.archive.service.IArchiveService#findDocuments(java.lang.String, java.util.Date)
     */
    @Override
    public List<DocumentMetadata> findDocuments(String personName, Date date) {
        return getDocumentDao().findByPersonNameDate(personName, date);
    }
    
    /**
     * Returns the document file from the archive
     * @see org.finratest.archive.service.IArchiveService#getDocumentFile(java.lang.String)
     */
    @Override
    public byte[] getDocumentFile(String id) {
        Document document = getDocumentDao().load(id);
        if(document!=null) {
            return document.getFileData();
        } else {
            return null;
        }
    }


    public IDocumentDao getDocumentDao() {
        return DocumentDao;
    }

    public void setDocumentDao(IDocumentDao documentDao) {
        DocumentDao = documentDao;
    }


}
