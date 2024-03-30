package controller;

import bean.Blog;
import bean.PostCate;
import bean.User;
import dao.BlogDAO;
import dao.PostCateDAO;
import dao.impl.BlogDAOImpl;
import dao.impl.PostCateDAOImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class PostDetailController extends HttpServlet {

    private static final String DEFAULT_FILENAME = "default.file";

    /**
     * Function Post Detail: allow the marketing member to update blog
     * information
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            BlogDAO blogDAO = new BlogDAOImpl();
            PostCateDAO postCateDAO = new PostCateDAOImpl();

            /**
             * get all post categories in the database
             */
            if (service.equalsIgnoreCase("getPostCategory")) {
                //get all post categories in the database
                ArrayList<PostCate> categoryList = postCateDAO.getAllPostCates();
                request.setAttribute("categoryList", categoryList);
                String message = request.getParameter("message");
                if (message != null) {
                    request.setAttribute("message", message);
                }
                request.getRequestDispatcher("jsp/postDetail.jsp").forward(request, response);
            }

            /**
             * get all information to display in the postDetail to edit post
             */
            if (service.equalsIgnoreCase("editPost")) {
                int blogId = Integer.parseInt(request.getParameter("blogId"));
                String message = request.getParameter("message");
                Blog editBlog = blogDAO.getBlogById(blogId);
                //get informations from database
                ArrayList<PostCate> categoryList = postCateDAO.getAllPostCates();
                ArrayList<PostCate> blogList = blogDAO.getBlogCategory(blogId);
                ArrayList<Integer> blogCategoryId = new ArrayList<>();
                for (PostCate blog : blogList) {
                    blogCategoryId.add(blog.getPostCateId());
                }
                request.setAttribute("editBlog", editBlog);
                request.setAttribute("categoryList", categoryList);
                request.setAttribute("blogCategoryId", blogCategoryId);
                if (message != null) {
                    request.setAttribute("message", message);
                }
                request.getRequestDispatcher("jsp/postDetail.jsp").forward(request, response);
            }

            /**
             * get input information in the postDetail to create new post
             */
            if (service.equalsIgnoreCase("addBlog")) {
                User currUser = (User) request.getSession().getAttribute("currUser");
                Blog newBlog = new Blog();
                ArrayList<PostCate> categoryList = postCateDAO.getAllPostCates();
                ArrayList<Integer> blogCategoryId = new ArrayList<>();
                int length = getServletContext().getRealPath("/").length();
                String uploadPath = new StringBuilder(getServletContext().getRealPath("/")).delete(length - 10, length - 4).toString() + File.separator + "images";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String fileName = "";
                for (Part part : request.getParts()) {
                    fileName = getFileName(part);
                    if (!fileName.equals(DEFAULT_FILENAME)) {
                        part.write(uploadPath + File.separator + fileName);
                        break;
                    }
                }
                fileName += "images/" + fileName;
                String postTitle = request.getParameter("postTitle");
                String detail = request.getParameter("detail");

                //get all cotegory id that user have input
                for (PostCate postCate : categoryList) {
                    String cateId = request.getParameter("categories_" + postCate.getPostCateId());
                    if (cateId != null) {
                        blogCategoryId.add(Integer.parseInt(cateId));
                    }
                }

                //if the postTitle or detail have yet entered then return message
                if (postTitle.length() == 0 || detail.length() == 0) {
                    request.setAttribute("message", "You have to enter all required form");
                    request.getRequestDispatcher("PostDetailController?service=getPostCategory").forward(request, response);
                    return;
                }
                //if  the postTitle or detail is too long then return message
                if (postTitle.length() >= 100 || detail.length() >= 1000) {
                    request.setAttribute("message", "Your input is to long!!!");
                    request.getRequestDispatcher("PostDetailController?service=getPostCategory").forward(request, response);
                    return;
                }
                //if user not check any category return message
                if (blogCategoryId.size() == 0) {
                    request.setAttribute("message", "Your post must have at least 1 category !!!");
                    request.getRequestDispatcher("PostDetailController?service=getPostCategory").forward(request, response);
                    return;
                }

                newBlog.setBlogTitle(postTitle);
                newBlog.setDetail(detail);
                newBlog.setAuthor(currUser);
                newBlog.setThumbnail(fileName);
                //add new blog
                blogDAO.addBlog(newBlog);
                // get blogId that have just been added
                int createdBlogId = blogDAO.getCreatedBlogID(newBlog);
                //input blog's categories
                for (Integer integer : blogCategoryId) {
                    blogDAO.addBlogCategory(createdBlogId, integer);
                }
                request.setAttribute("message", "Your blog have successfull posted !!!");
                request.getRequestDispatcher("PostDetailController?service=getPostCategory").forward(request, response);
            }

            /**
             * get information from postDetail to edit existed blog in the
             * database
             */
            if (service.equalsIgnoreCase("editBlog")) {
                User currUser = (User) request.getSession().getAttribute("currUser");
                ArrayList<PostCate> categoryList = postCateDAO.getAllPostCates();
                ArrayList<Integer> blogCategoryId = new ArrayList<>();
                try {
                    int length = getServletContext().getRealPath("/").length();
                    String uploadPath = new StringBuilder(getServletContext().getRealPath("/")).delete(length - 10, length - 4).toString() + File.separator + "images";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    String fileName = "";
                    for (Part part : request.getParts()) {
                        fileName = getFileName(part);
                        if (!fileName.equals(DEFAULT_FILENAME)) {
                            part.write(uploadPath + File.separator + fileName);
                            break;
                        }
                    }
                    fileName += "images/" + fileName;
                    String postTitle = request.getParameter("postTitle");
                    String detail = request.getParameter("detail");
                    int editBlogId = Integer.parseInt(request.getParameter("editBlogId"));
                    Blog editBlog = blogDAO.getBlogById(editBlogId);
                    //get all cotegory id that user have input
                    for (PostCate postCate : categoryList) {
                        String cateId = request.getParameter("categories_" + postCate.getPostCateId());
                        if (cateId != null) {
                            blogCategoryId.add(Integer.parseInt(cateId));
                        }
                    }

                    //if the postTitle or detail have yet entered then return message
                    if (postTitle.length() == 0 || detail.length() == 0) {
                        request.setAttribute("message", "You have to enter all required form");
                        request.getRequestDispatcher("PostDetailController?service=editPost&blogId=" + editBlogId).forward(request, response);
                    }
                    //if  the postTitle or detail is too long then return message
                    if (postTitle.length() >= 100 || detail.length() >= 1000) {
                        request.setAttribute("message", "Your input is to long!!!");
                        request.getRequestDispatcher("PostDetailController?service=editPost&blogId=" + editBlogId).forward(request, response);
                    }
                    //if user not check any category return message
                    if (blogCategoryId.size() == 0) {
                        request.setAttribute("message", "Your post must have at least 1 category !!!");
                        request.getRequestDispatcher("PostDetailController?service=editPost&blogId=" + editBlogId).forward(request, response);
                        return;
                    }
                    //set new attribute
                    editBlog.setBlogTitle(postTitle);
                    editBlog.setDetail(detail);
                    editBlog.setAuthor(currUser);
                    //if there is a new thumnail then set thumnail attribute
                    if (fileName != null && !fileName.equals("")) {
                        editBlog.setThumbnail(fileName);
                    }
                    //add new blog
                    blogDAO.editBlog(editBlog.getBlogId(), editBlog);
                    //remove all old categories
                    blogDAO.removeAllBlogCategory(editBlogId);
                    //input new blog's categories
                    for (Integer integer : blogCategoryId) {
                        blogDAO.addBlogCategory(editBlogId, integer);
                    }
                    request.setAttribute("message", "Your blog have successfull edited !!!");
                    request.getRequestDispatcher("PostDetailController?service=editPost&blogId=" + editBlogId).forward(request, response);
                } catch (FileUploadException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDetailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return DEFAULT_FILENAME;
    }
}
