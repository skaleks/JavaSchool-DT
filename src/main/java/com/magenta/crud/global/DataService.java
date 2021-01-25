package com.magenta.crud.global;

import com.magenta.crud.contract.ContractService;
import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.ContractPageDto;
import com.magenta.crud.global.dto.AdminMainDto;
import com.magenta.crud.option.OptionService;
import com.magenta.crud.option.dto.OptionPageDto;
import com.magenta.crud.tariff.TariffService;
import com.magenta.crud.tariff.dto.TariffPageDto;
import com.magenta.crud.user.UserService;
import com.magenta.crud.user.dto.UserDto;
import com.magenta.crud.user.dto.UserProfileDto;
import com.magenta.myexception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

@Service("pageFromDataService")
@Transactional
public class DataService {

    private static final Logger LOGGER = Logger.getLogger("DataService");

    private final UserService userService;
    private final TariffService tariffService;
    private final OptionService optionService;
    private final ContractService contractService;

    @Autowired
    public DataService(UserService userService, TariffService tariffService, OptionService optionService, ContractService contractService) {
        this.userService = userService;
        this.tariffService = tariffService;
        this.optionService = optionService;
        this.contractService = contractService;
    }

//    public UserMainDto getMainPageForUser(String login) {
//        return new UserMainDto();
//    }
    public AdminMainDto getMainPageForAdmin(String login) {
        return new AdminMainDto();
    }

    public UserProfileDto getUserProfileByLogin(String login) throws DatabaseException {

        UserDto user = userService.findByLogin(login);
        List<ContractDto> contracts = user.getNumbers();
        return new UserProfileDto(user,contracts);
    }
    public UserProfileDto getUserProfileById(int id) throws DatabaseException {

        UserDto user = userService.findById(id);
        List<ContractDto> contracts = user.getNumbers();
        return new UserProfileDto(user,contracts);
    }

    public UserProfileDto getUserProfileByNumberOrEmail(String request) throws DatabaseException {

        UserDto user = userService.findByNumberOrEmail(request);
        List<ContractDto> contracts = user.getNumbers();
        return new UserProfileDto(user,contracts);
    }

    public TariffPageDto getTariffPage(int id) throws DatabaseException {
        return new TariffPageDto(tariffService.findTariffById(id),new HashSet<>(optionService.findAllOptions()));
    }

    public OptionPageDto getOptionPage(int id) throws DatabaseException {
        return new OptionPageDto(optionService.findOptionById(id));
    }

    public ContractPageDto getContractPage(int id) throws DatabaseException {
        return new ContractPageDto(contractService.findById(id));
    }

}
