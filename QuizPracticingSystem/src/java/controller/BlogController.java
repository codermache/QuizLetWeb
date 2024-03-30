
package controller;

import bean.Blog;
import bean.PostCate;
import dao.BlogDAO;
import dao.PostCateDAO;
import dao.impl.BlogDAOImpl;
import dao.impl.PostCateDAOImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BlogController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Function: BLogList: allow user to see all blog Function:
     * BlogDetail: allow user to read blog
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static final int CARD_PER_PAGE = 9;
    static final int DEFAULT_PAGE = 1;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String service = request.getParameter("service");
            BlogDAO blogInterface = new BlogDAOImpl();
            PostCateDAO postCateInterface = new PostCateDAOImpl();

            if (service.equalsIgnoreCase("blogList")) {
                ArrayList<Blog> blogList = blogInterface.getAllTrueBlog();

                //neu tim kiem theo category hoac string
                String[] searchCate = request.getParameterValues("category");
                String searchString = request.getParameter("search");
                if ((searchCate != null) || (searchString != null)) {
                    if (searchString.length() > 100) {
                        request.setAttribute("errorMess", "invalid length");
                    } else {
                        blogList = blogInterface.getBlogByCategoryAndTitle(searchCate, searchString);
                        //url concat to ...?page=
                        String pagingUrl = "";
                        //Add category parameter
                        if (searchCate != null) {
                            for (String category : searchCate) {
                                pagingUrl += "&category=" + category;
                            }
                            int[] cateList = new int[searchCate.length];
                            for (int i = 0; i < searchCate.length; i++) {
                                cateList[i] = Integer.parseInt(searchCate[i]);
                            }
                            request.setAttribute("cateList", cateList);
                        }
                        //Add search parameter
                        if (searchString != null) {
                            pagingUrl += "&search=" + searchString;
                            request.setAttribute("search", searchString);
                        }
                        request.setAttribute("pagingUrl", pagingUrl);
                    }
                }

                //xu li phan trang
                int listSize = blogList.size();
                int pageNumber = (listSize % CARD_PER_PAGE == 0) ? (listSize / CARD_PER_PAGE) : (listSize / CARD_PER_PAGE + 1); //Number of pages needed 
                String pageRaw = request.getParameter("page");
                int page;
                if (pageRaw == null) {
                    page = DEFAULT_PAGE;
                } else {
                    page = Integer.parseInt(pageRaw);
                }
                request.setAttribute("pagenum", pageNumber);
                request.setAttribute("page", page);

                ArrayList<Blog> paginatedBlogList = blogInterface.Paging(page, blogList);
                request.setAttribute("blogList", paginatedBlogList);
                //Send blog category list
                ArrayList<PostCate> postCateList = postCateInterface.getAllPostCates();
                request.setAttribute("postCateList", postCateList);
                //Send last blogs
                ArrayList<Blog> lastBlogs = blogInterface.getLastBlogs();
                request.setAttribute("lastBlogs", lastBlogs);

                request.getRequestDispatcher("jsp/blogList.jsp").forward(request, response);
            }

            /**
             * Service blog detail: show detail of the blog
             */
            if (service.equalsIgnoreCase("blogDetail")) {
                int blogId = Integer.parseInt(request.getParameter("blogId"));
                Blog blog = blogInterface.getBlogById(blogId);
                request.setAttribute("blog", blog);
                int blogCate = postCateInterface.getBlogCateByBlogId(blogId);
                String blogCateName = postCateInterface.getPostCateById(blogCate)
                        .getPostCateName();
                request.setAttribute("blogCateName", blogCateName);
                ArrayList<PostCate> postCateList = postCateInterface.getAllPostCates();
                request.setAttribute("postCateList", postCateList);
                ArrayList<Blog> lastBlogs = blogInterface.getLastBlogs();
                request.setAttribute("lastBlogs", lastBlogs);
                request.getRequestDispatcher("jsp/blogDetail.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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

}
