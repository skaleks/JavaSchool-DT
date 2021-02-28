package com.magenta.sessioncart;


import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.EditContractDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("cart")
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
public class SessionCart {

    private static final Logger LOGGER = LogManager.getLogger(SessionCart.class);

    private Map<String, Set<OptionDto>> cart = new HashMap<>();
    private int itemsCount;
    private ContractService contractService;
    private OptionService optionService;

    @Autowired
    public SessionCart(ContractService contractService, OptionService optionService) {
        this.contractService = contractService;
        this.optionService = optionService;
    }

    public void addOptionsToCart(EditContractDto editContractDto) throws DatabaseException, MyException {

        ContractDto contractDto = contractService.findById(editContractDto.getContractId());
        OptionDto optionDto = optionService.findOptionById(editContractDto.getOptionId());
        String key = contractDto.getNumber();

        if (cart.containsKey(key)){
            Set<OptionDto> currentSet = cart.get(key);
            if (currentSet.contains(optionDto)){
                throw new MyException("This option already added");
            }
            currentSet.add(optionDto);
            cart.put(key,currentSet);
        }else {
            Set<OptionDto> newSet = new HashSet<>();
            newSet.add(optionDto);
            cart.put(key,newSet);
        }

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

    public int getItemsCount() {
        itemsCount = 0;
        cart.forEach((k,v) -> itemsCount+=v.size());
        return itemsCount;
    }
}
