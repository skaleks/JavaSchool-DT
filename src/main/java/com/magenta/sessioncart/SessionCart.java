package com.magenta.sessioncart;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.EditContractDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import com.magenta.security.SecurityService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.*;

@Component("cart")
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class SessionCart implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(SessionCart.class);

    private int itemsCount;
    private double totalPrice;
    private Map<String, HashMap<String, Set<OptionDto>>> session = new HashMap<>();
    private HashMap<String, Set<OptionDto>> cart;

    private ContractService contractService;
    private OptionService optionService;
    private SecurityService securityService;

    @Autowired
    public SessionCart(ContractService contractService, OptionService optionService, SecurityService securityService) {
        this.contractService = contractService;
        this.optionService = optionService;
        this.securityService = securityService;
    }

    public void setCart(HashMap<String, Set<OptionDto>> newCart) {
        session.put(securityService.getPrincipal(), newCart);
        cart = newCart;
    }

    public HashMap<String, Set<OptionDto>> getCart(){

        HashMap<String, Set<OptionDto>> currentCart = session.get(securityService.getPrincipal());

        if (currentCart == null){
            HashMap<String,Set<OptionDto>> newCart = new HashMap<>();
            setCart(newCart);
            LOGGER.error("Новый пользователь получил свою карту");
            return cart;
        }else {
            setCart(currentCart);
            LOGGER.error("Существующий пользователь получил свою карту");
        }
        return cart;
    }

    public void addOptionsToCart(EditContractDto editContractDto) throws DatabaseException, MyException {

        ContractDto contractDto = contractService.findById(editContractDto.getContractId());
        OptionDto optionDto = optionService.findOptionById(editContractDto.getOptionId());
        String key = contractDto.getNumber();

        LOGGER.info(securityService.getPrincipal() + " с номером: " + key);

        if (cart.containsKey(key)){
            Set<OptionDto> currentSet = cart.get(key);
            LOGGER.info("Номер: " + key);
            for (OptionDto option: currentSet) {
                LOGGER.error(option.getName());
            }
            if (currentSet.contains(optionDto)){
                LOGGER.info("Карта имеет: " + currentSet.size() + "опций");
                LOGGER.info("Опция уже добавлена");
                throw new MyException("This option already added");
            }
            currentSet.add(optionDto);
            LOGGER.info("Опция" + optionDto.getName() + "только что успешно добавлена");
            cart.put(key,currentSet);
        }else {
            Set<OptionDto> newSet = new HashSet<>();
            newSet.add(optionDto);
            LOGGER.info("Опция " + optionDto.getName() + " является первой в карте");
            cart.put(key,newSet);
        }
//        setCart(cart);
    }

    public void deleteOptionFromCart(EditContractDto editContractDto) throws DatabaseException {

        ContractDto contractDto = contractService.findById(editContractDto.getContractId());
        OptionDto optionDto = optionService.findOptionById(editContractDto.getOptionId());
        String key = contractDto.getNumber();

        if (cart.containsKey(key)){
            Set<OptionDto> currentSet = cart.get(key);
            currentSet.removeIf(option -> option.equals(optionDto));
            cart.put(key, currentSet);
        }
    }

    int logCount = 0;
    public int getItemsCount() {
        ++logCount;
        LOGGER.info("Счетчик вхождений " + logCount);
        itemsCount = 0;
        getCart().forEach((k,v) -> itemsCount+=v.size());
        LOGGER.error("Пользователь с именем " + securityService.getPrincipal() + " имеет " + itemsCount + " опций в корзине");
        return itemsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionCart that = (SessionCart) o;
        return itemsCount == that.itemsCount && Double.compare(that.totalPrice, totalPrice) == 0 && logCount == that.logCount && Objects.equals(session, that.session) && Objects.equals(cart, that.cart) && Objects.equals(contractService, that.contractService) && Objects.equals(optionService, that.optionService) && Objects.equals(securityService, that.securityService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemsCount, totalPrice, session, cart, contractService, optionService, securityService, logCount);
    }
}
