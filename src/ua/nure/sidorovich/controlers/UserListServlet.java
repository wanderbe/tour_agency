package ua.nure.sidorovich.controlers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.sidorovich.entety.BlockedUser;
import ua.nure.sidorovich.entety.User;
import ua.nure.sidorovich.service.BlockedUserService;
import ua.nure.sidorovich.service.BlockedUserServiceImpl;
import ua.nure.sidorovich.service.UserService;
import ua.nure.sidorovich.service.UserServiceImpl;
import ua.nure.sidorovich.util.DataBaseException;


@WebServlet("/users")
public class UserListServlet extends HttpServlet {

	private static final long serialVersionUID = 8124141131832201185L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		UserService userService = new UserServiceImpl();
		
		try {
			List<User> resultList = userService.getList();
			request.setAttribute("userList", resultList);		
			
			request.getRequestDispatcher("WEB-INF/UserList.jsp").forward(request, response);
		} catch (DataBaseException e) {
			response.sendError(404, e.getMessage());
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		UserService userService = new UserServiceImpl();
		BlockedUserService blockedUserDao = new BlockedUserServiceImpl();
		
		if(request.getParameter("idUser") == null || request.getParameter("idUser").length() == 0){
			response.sendRedirect("users");
		} else {
			try {				
				if(blockedUserDao.getByUser(userService.getByID(Long.parseLong(request.getParameter("idUser")))) 
						== null){					
					BlockedUser blockedUser = new BlockedUser();
					blockedUser.setBlockTime(new Timestamp(System.currentTimeMillis()));
					blockedUser.setUser(userService.getByID(Long.parseLong(request.getParameter("idUser"))));
					blockedUser.setDescription(" -not realised yet- ");
					
					blockedUserDao.save(blockedUser);				
				} else {
					blockedUserDao
					.remove(blockedUserDao.getByUser(userService.getByID(Long.parseLong(request.getParameter("idUser")))));
				}

				response.sendRedirect("users");
			} catch (NumberFormatException e) {
				response.sendRedirect("users");
			} catch (DataBaseException e) {
				response.sendError(404, e.getMessage());
			}
		}
	}
}
