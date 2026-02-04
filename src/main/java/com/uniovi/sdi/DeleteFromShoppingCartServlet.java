package com.uniovi.sdi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name="DeleteFromShoppingCartServlet",value="/DeleteFromShoppingCart")
public class DeleteFromShoppingCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        HashMap<String, Integer> cart =
                (HashMap<String, Integer>) session.getAttribute("cart");
        if(cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }
        String product = request.getParameter("product");
        String method = request.getParameter("_method");
        if(product != null && "DELETE".equalsIgnoreCase(method)) {
            deleteFromShoppingCart(cart, product);
        }
        request.setAttribute("selectedItems", cart);
        getServletContext().getRequestDispatcher("/cart.jsp").forward(request, response);

    }

    private void deleteFromShoppingCart(HashMap<String, Integer> cart, String product){
        if(cart.containsKey(product)) {
            if (cart.get(product) <= 1) cart.remove(product);
            else cart.put(product, cart.get(product) - 1);
        }
    }
}

