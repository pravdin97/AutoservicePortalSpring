package com.example.AutoservicePortal.Controller;

import com.example.AutoservicePortal.Repository.*;
import com.example.AutoservicePortal.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ServiceRepo serviceRepo;

    @Autowired
    private AutoserviceRepo autoserviceRepo;

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RequestRepo requestRepo;

    @GetMapping("/")
    public String index(Map<String, Object> model) {

        Iterable<Offer> offers = offerRepo.findAll();
        model.put("offers", offers);

        return "index";
    }

    @PostMapping("/")
    public String validSignUp(Map<String, Object> model) {

        index(model);
        return "index";
    }

    @PostMapping("/oneitem")
    public String oneitem(@RequestParam Integer view, Map<String, Object> model) {

        Optional<Offer> offer = offerRepo.findById(view);

        Offer o = offer.get();
        model.put("service", o.getServiceName());
        model.put("autoservice", o.getAutoserviceName());
        model.put("text", o.getText());
        model.put("address", o.getAutoservice().getAddress());
        model.put("cost", o.getCost());


        return "oneitem";
    }

    @GetMapping("/offers")
    public String offers(Principal principal, Map<String, Object> model) {
        User user = userRepo.findByUsername(principal.getName());
        Optional<Autoservice> autoservice = autoserviceRepo.findById(user.getAutoservice().getId());
        Autoservice a = autoservice.get();

        List<Offer> offers = offerRepo.findByAutoservice(a);

        model.put("offers", offers);

        return "offers";
    }

    @PostMapping("/offers")
    public String crud(Principal principal,
                       @RequestParam(name = "save", required = false, defaultValue = "false") String save,
                       @RequestParam(name = "create", required = false, defaultValue = "false") String create,
                       @RequestParam(name = "delete", required = false) Integer delete,
                       @RequestParam(required = false) Integer id,
                       @RequestParam(required = false) String text,
                       @RequestParam(required = false) Integer cost,
                       @RequestParam(required = false) Integer service,
                       @RequestParam(required = false) Integer autoservice,
            Map<String, Object> model)
    {
        if (create.equals("true")) {
            Optional<Service> service1 = serviceRepo.findById(service);
            Optional<Autoservice> autoservice1 = autoserviceRepo.findById(autoservice);
            Offer offer = new Offer(text, cost, service1.get(), autoservice1.get());
            offerRepo.save(offer);
        }

        if (save.equals("true")) {
            Optional<Service> service1 = serviceRepo.findById(service);
            Optional<Autoservice> autoservice1 = autoserviceRepo.findById(autoservice);

            Optional<Offer> offer = offerRepo.findById(id);
            Offer o = offer.get();
            o.setText(text);
            o.setCost(cost);
            o.setService(service1.get());
            o.setAutoservice(autoservice1.get());

            offerRepo.save(o);
        }

        if (delete != null) {
            offerRepo.deleteById(delete);
        }

        offers(principal, model);

        return "offers";
    }

    @PostMapping("/edit")
    public String edit(
            @RequestParam Integer edit,
            Map<String, Object> model) {
        Optional<Offer> offer = offerRepo.findById(edit);
        Offer o = offer.get();

        Optional<Service> service1 = serviceRepo.findById(o.getService().getId());
        Optional<Autoservice> autoservice1 = autoserviceRepo.findById(o.getAutoservice().getId());


        model.put("id", edit);
        model.put("autoservice", autoservice1.get().getId());
        model.put("serviceid", service1.get().getId());
        model.put("service", service1.get().getName());
        model.put("text", o.getText());
        model.put("cost", o.getCost());

        Iterable<Service> services = serviceRepo.findByIdNot(service1.get().getId());
        model.put("services", services);
        model.put("update", true);

        return "edit";
    }

    @GetMapping("/edit")
    public String create(Principal principal, Map<String, Object> model) {
        User user = userRepo.findByUsername(principal.getName());
        model.put("autoservice", user.getAutoservice().getId());

        Iterable<Service> services = serviceRepo.findAll();
        model.put("services", services);

        model.put("id", 0);
        model.put("text", "");
        model.put("cost", "");
        model.put("service", -1);

        model.put("update", false);

        return "edit";
    }
    @PostMapping("/signup")
    public String signup(@RequestParam String service,
            Map<String, Object> model) {
        model.put("service", service);
        return "signup";
    }

    @PostMapping("/validsignup")
    public void acceptSignUp(Map<String, Object> model) {
        index(model);
    }
}