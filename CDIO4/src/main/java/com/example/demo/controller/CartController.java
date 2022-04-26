package com.example.demo.controller;
import com.example.demo.config.MyConstants;
import com.example.demo.model.*;

import com.example.demo.service.auctionuser.AuctionUserService;
import com.example.demo.service.paypal.PaypalPaymentIntent;
import com.example.demo.service.paypal.PaypalPaymentMethod;
import com.example.demo.service.paypal.PaypalService;
import com.example.demo.util.PaypalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ProductBillService.BillService;
import com.example.demo.service.User.UserService;

import com.example.demo.service.product.ProductService;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    BillService billService;
    @Autowired
    ProductService productService;
    @Autowired
    AuctionUserService auctionUserService;


    public static  int[]  arrayQuantily = new int[10];
    public static int[] temp = new int[10];
    public static double totalMoney = 0;
    public static double sumTotalMoney=0;
    public static int totalSoLuong = 0;
    public static ProductBill chiTietDonHangTemp = new ProductBill();
    public static HashMap<Double, Product> listSpHoaDonTemp = new HashMap<>();
    @ModelAttribute("nguoiDung")
    public User getDauGia() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByAccount_UserName(auth.getName());
    }

//    @GetMapping("/Cart/LayDuLieu")
//    public String show(@RequestParam String soluongs,@RequestParam String tongTien,Model model) {
//        totalMoney = Double.parseDouble(tongTien);
//        totalSoLuong = Integer.parseInt(soluongs);
//        System.out.println("totalMoney:"+totalMoney);
//        System.out.println("totalMoney:"+totalSoLuong);
//        model.addAttribute("tongtien",tongTien);
//        model.addAttribute("soluongs",soluongs);
//        model.addAttribute("cart",new Cart());
//        return "Vinh/CartPage";
//    }
    @GetMapping("showCart")
    public ModelAndView show(@SessionAttribute("carts") HashMap<Integer, Cart> cartMap) {
        ModelAndView modelAndView = new ModelAndView("Vinh/CartPage");
        modelAndView.addObject("carts", cartMap);
        modelAndView.addObject("cartSize", cartMap.size());
        return modelAndView;
    }
    @GetMapping("/showCart/LayDuLieu/{id}")
    public String show(@PathVariable int id,@RequestParam String soluongs,@RequestParam String tongTien,Model model,@SessionAttribute("carts") HashMap<Integer, Cart> cartMap) {
        totalMoney = Double.parseDouble(tongTien);
        sumTotalMoney +=totalMoney;
        totalSoLuong = Integer.parseInt(soluongs);

        extracted(id, arrayQuantily, temp);
        System.out.println("totalMoney:"+totalMoney);
        System.out.println("totalMoney:"+totalSoLuong);
        model.addAttribute("tongtien",tongTien);
        model.addAttribute("soluongs",soluongs);
        model.addAttribute("cart",new Cart());
        if (cartMap == null) {
            cartMap = new HashMap<>();
        }
        Product product = productService.findById(id);
        if (product != null) {
            for(int i=0;i<10;i++){
                if(arrayQuantily[i]==id) {
                    product.setQuantity(product.getQuantity() - temp[i]);
                    if (product.getQuantity() < 1) {
                        product.setStatus("Hết sản phẩm");
                    }
                }
            }
            productService.create(product);
            if (cartMap.containsKey(id)) {
                Cart item = cartMap.get(id);
                item.setProduct(product);
                item.setQuantity(item.getQuantity() + totalSoLuong);
                item.setMaxPrice(totalMoney);
                cartMap.put(id, item);
            } else {
                Cart cart = new Cart();
                cart.setProduct(product);
                cart.setQuantity(totalSoLuong);
                cart.setMaxPrice(totalMoney);
                cartMap.put(id, cart);
            }
        }
        model.addAttribute("carts", cartMap);
        model.addAttribute("cartSize", cartMap.size());
        return "redirect:/showCart";
    }
    @GetMapping("/showCart/addCart/{id}")
    public String addToCart(@PathVariable int id, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap, Model model) {
        if (cartMap == null) {
            cartMap = new HashMap<>();
        }
            Product product = productService.findById(id);
        List<AuctionUser> detailList = auctionUserService.findByProduct(id);
        double giaCaoNhat = detailList.get(0).getStartingPrice();
        if (product != null) {
            product.setQuantity(product.getQuantity() - 1);
            productService.create(product);
            if (cartMap.containsKey(id)) {
                Cart item = cartMap.get(id);
                item.setProduct(product);
                item.setQuantity(item.getQuantity() + 1);
                item.setMaxPrice(giaCaoNhat);
                cartMap.put(id, item);
            } else {
                Cart cart = new Cart();
                cart.setProduct(product);
                cart.setQuantity(1);
                cart.setMaxPrice(giaCaoNhat);
                cartMap.put(id, cart);
            }
        }
        model.addAttribute("carts", cartMap);
        model.addAttribute("cartSize", cartMap.size());
        return "redirect:/showCart";
    }

    private void extracted(@PathVariable int id, int[] arrayQuantily, int[] temp) {
        for(int i=0;i<10;i++){
            if(arrayQuantily[i]==0){
                arrayQuantily[i]=id;
                temp[i] = totalSoLuong;
                break;
            }

        }
    }
//    @GetMapping("/show/LayDuLieu")
//    public String addToCart(@PathVariable int id, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap, Model model) {
//        if (cartMap == null) {
//            cartMap = new HashMap<>();
//        }
//        Cart cart = new Cart();
//        cartMap.put(id,cart);
//        model.addAttribute("carts", cartMap);
//        model.addAttribute("cartSize", cartMap.size());
//        return "Vinh/CartPage";
//    }

    @GetMapping("/deleteCart/{id}")
    public String deleteCart(@PathVariable int id, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap, Model model) {
        cartMap.remove(id);
        extracted(id,arrayQuantily,temp);
        Product product = productService.findById(id);
        if (product != null) {
            for(int i=0;i<10;i++){
                if(arrayQuantily[i]==id){
                    System.out.println("xoa " +temp[i]);
                    product.setQuantity(product.getQuantity() + temp[i]);
                    if(product.getQuantity()>=1) {
                        product.setStatus("Đã duyệt");
                    }
                    break;
                }
            }
            productService.create(product);
        }
        return "redirect:/showCart";
    }
    @GetMapping("/hoaDon/layDuLieu/{id}")
    public String getHoaDon(@PathVariable int id, Model model, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap) {
        if (cartMap == null) {
            cartMap = new HashMap<>();
        }
        Product product = productService.findById(id);
        if (product != null) {

            product.setQuantity(product.getQuantity() - totalSoLuong);
            if(product.getQuantity()<=0){
                product.setStatus("Chưa duyệt");
            }
            productService.create(product);
            if (cartMap.containsKey(id)) {
                Cart item = cartMap.get(id);
                item.setProduct(product);
                item.setQuantity(item.getQuantity() + totalSoLuong);
                item.setMaxPrice(totalMoney);
                cartMap.put(id, item);
            } else {
                Cart cart = new Cart();
                cart.setProduct(product);
                cart.setQuantity(totalSoLuong);
                cart.setMaxPrice(totalMoney);
                cartMap.put(id, cart);
            }
        }
        model.addAttribute("donHang", new Bill());
        model.addAttribute("carts",cartMap);
        return "Vinh/Pay";
    }
    @GetMapping("/hoaDon/thanhToan")
    public String thanhToan(@SessionAttribute("carts") HashMap<Integer, Cart> cartMap, @ModelAttribute Bill bill, Model model
    ) {
        Double inputTotal = sumTotalMoney;
        List<String> tenSp = new ArrayList<>();
        for (Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            tenSp.add(value.getProduct().getProductName());
        }
        HashMap<Double, Product> listSpHoaDon = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUser_User(auth.getName());
        LocalDate currentDate = java.time.LocalDate.now();
        bill.setDateOder(String.valueOf(currentDate));
        bill.setStatus("Đang giao");
        bill.setUser(user);
        bill.setTotalCost(totalMoney);
        billService.create(bill);
        ProductBillKey productBillKey = new ProductBillKey();
        ProductBill productBill = new ProductBill();
        for (Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            listSpHoaDon.put(value.getMaxPrice(), value.getProduct());
            productBillKey.setInBill(bill.getInBill());
            productBillKey.setInBill(value.getProduct().getIdProduct());
            productBill.setBill(bill);
            productBill.setId(productBillKey);
            productBill.setProduct(value.getProduct());
            productBill.setQuantity(totalSoLuong);
            productBill.setPrice(value.getMaxPrice());
            billService.createChiTiet(productBill);
//            cartMap.remove(entry.getKey());
//            sumTotalMoney-=totalMoney;
        }

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("thangit189@gmail.com");
//        message.setTo(MyConstants.FRIEND_EMAIL);
//        message.setSubject("THÔNG BÁO ĐÃ THANH TOÁN HÓA ĐƠN!");
//        message.setText("Mã hóa đơn: HD" + chiTietDonHang.getDonHang().getMaDonHang() + "\n" +
//                "Danh sách sản phẩm: " + tenSp + "\n" +
//                "Ngày mua: " + chiTietDonHang.getDonHang().getNgayMua() + "\n" +
//                "Số tiền đã thanh toán: " + totalMoney);
//        this.emailSender.send(message);
        model.addAttribute("tongtien",inputTotal);
        model.addAttribute("carts",cartMap);
        model.addAttribute("hoaDon", chiTietDonHangTemp);
        model.addAttribute("listSp", listSpHoaDonTemp);
        model.addAttribute("hoaDon", productBill);
        model.addAttribute("listSp", listSpHoaDon);
        return "Vinh/ReceiptPage";
    }

    @GetMapping("/paypal")
    public String index(){
        return "paypal/index";
    }

    @GetMapping("/pay")
    public String pay(HttpServletRequest request, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap, @ModelAttribute Bill bill,
                      Model model) {
        HashMap<Double, Product> listSpHoaDon = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUser_User(auth.getName());
        LocalDate currentDate = java.time.LocalDate.now();
        bill.setDateOder(String.valueOf(currentDate));
        bill.setStatus("Đang giao");
        bill.setUser(user);
        bill.setTotalCost(totalMoney);
        billService.create(bill);
        ProductBillKey productBillKey = new ProductBillKey();
        ProductBill productBill = new ProductBill();
        for (Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            listSpHoaDon.put(value.getMaxPrice(), value.getProduct());
            productBillKey.setInBill(bill.getInBill());
            productBillKey.setInBill(value.getProduct().getIdProduct());
            productBill.setBill(bill);
            productBill.setId(productBillKey);
            productBill.setProduct(value.getProduct());
            productBill.setQuantity(totalSoLuong);
            productBill.setPrice(value.getMaxPrice());
            billService.createChiTiet(productBill);
        }
        chiTietDonHangTemp = productBill;
        listSpHoaDonTemp = listSpHoaDon;
        String cancelUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = PaypalUtils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    sumTotalMoney,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "paypal/cancel";
    }
    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model, @SessionAttribute("carts") HashMap<Integer, Cart> cartMap, Principal principal){
        Double inputTotal = sumTotalMoney;
        List<String> tenSp = new ArrayList<>();
        User user = userRepo.findByAccount_UserName(principal.getName());
        MyConstants.setFriendEmail(user.getGmail());
        for (Map.Entry<Integer, Cart> entry : cartMap.entrySet()) {
            Cart value = entry.getValue();
            tenSp.add(value.getProduct().getProductName());
        }

        try {
            //Xóa hết cart trong giỏ hàng

            //
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("luytlong122@gmail.com");
                message.setTo(MyConstants.FRIEND_EMAIL);
                message.setSubject("THÔNG BÁO ĐÃ THANH TOÁN HÓA ĐƠN!");
                message.setText("Mã hóa đơn: HD" + chiTietDonHangTemp.getBill().getInBill() + "\n" +
                        "Danh sách sản phẩm: " + tenSp + "\n" +
                        "Ngày mua: " + chiTietDonHangTemp.getBill().getDateOder() + "\n" +
                        "Số tiền đã thanh toán: " + sumTotalMoney);
                this.emailSender.send(message);
                model.addAttribute("tongtien",inputTotal);
                model.addAttribute("carts",cartMap);
                model.addAttribute("hoaDon", chiTietDonHangTemp);
                model.addAttribute("listSp", listSpHoaDonTemp);
                return "Vinh/ReceiptPage";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        sumTotalMoney=0;
        return "redirect:/paypal";
    }
}
