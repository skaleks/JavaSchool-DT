package com.magenta.crud.option;

import com.magenta.checker.RuleCheckerForOptions;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.dto.AddRelativeToOption;
import com.magenta.crud.option.dto.DelRelativeFromOption;
import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.type.OptionRelative;
import com.magenta.crud.type.Status;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service("optionService")
@Transactional
public class OptionServiceImpl implements OptionService {

    private final static Logger LOGGER = Logger.getLogger("OptionService");

    private final OptionDao optionDao;
    private final ModelMapper modelMapper;
    private final RuleCheckerForOptions checker;

    @Autowired
    public OptionServiceImpl(OptionDao optionDao, ModelMapper modelMapper, RuleCheckerForOptions checker) {
        this.optionDao = optionDao;
        this.modelMapper = modelMapper;
        this.checker = checker;
    }

    @Override
    public void createNewOption(NewOptionDto newOptionDto) {
        Option option = modelMapper.map(newOptionDto, Option.class);
        option.setStatus(Status.ACTIVE);
        optionDao.createNewOption(option);
    }

    @Override
    public void deleteExistOption(int id) throws DatabaseException, MyException {

        Option deletedOption = optionDao.findOptionById(id);
        checker.checkIfStillUsed(deletedOption);
        optionDao.deleteExistOption(deletedOption);
    }

    @Override
    public List<OptionDto> findAllOptions() {
        return mapToOptionDtoList(optionDao.findAllOptions());
    }

    @Override
    public OptionDto findOptionById(int id) throws DatabaseException {
        return modelMapper.map(optionDao.findOptionById(id),OptionDto.class);
    }

    @Override
    public Set<Option> findSetOptionsById(List<Integer> optionsList) {
        return optionDao.findSetOptionsById(optionsList);
    }

    @Override
    public void updateOption(Option option) {
        optionDao.updateOption(option);
    }

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws DatabaseException {

        Option option = optionDao.findOptionById(statusDto.getEntityId());
        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        option.setStatus(newStatus);
    }

    @Override
    public List<OptionDto> findAvailableOptions(int id) throws DatabaseException {
        List<Option> resultList;
        List<Option> allOptionsList = optionDao.findAllOptions();

        resultList = checker.checkRuleAndGetResult(allOptionsList, id);
        return mapToOptionDtoList(resultList);
    }

    @Override
    public void addRelativeToOption(AddRelativeToOption addRelativeToOption) throws DatabaseException, MyException {
//        HashSet<Option> realRelatedOptions;
//        OptionRelative rule = OptionRelative.valueOf(addRelativeToOption.getOptionRelative());
//        Option targetOption = optionDao.findOptionById(addRelativeToOption.getTargetOptionId());
//        Option addedOption = optionDao.findOptionById(addRelativeToOption.getAddedOptionId());
//
//        if (rule.equals(OptionRelative.RELATED)) {
//
//            HashSet<Option> expectedSet = new HashSet<>();
//            HashSet<Option> topSet = findTop(targetOption);
//
//            for (Option tariffOptionTop : topSet) {
//
//                realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedOptions());
//                realRelatedOptions.add(tariffOptionTop);
//                expectedSet.addAll(realRelatedOptions);
//            }
//
//            topSet = findTop(addedOption);
//            for (Option tariffOptionTop : topSet) {
//                realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedOptions());
//                realRelatedOptions.add(tariffOptionTop);
//                expectedSet.addAll(realRelatedOptions);
//            }
//
//            for (Option option : expectedSet) {
//                option.getExcludedOptions().retainAll(expectedSet);
//                if (!option.getExcludedOptions().isEmpty()) {
//                    StringBuilder sb = new StringBuilder();
//                    option.getExcludedOptions().forEach(anotherOption -> sb.append(anotherOption.getName()).append(";"));
//                    String exceptionMessage = "Options " + sb.toString() + " exclude " + option.getName() + ".";
//                    throw new MyException(exceptionMessage);
//                }
//            }
//            addedOption.getLeadOptions().add(targetOption);
//            targetOption.getRelatedOptions().add(addedOption);
//        }
//        if (rule.equals(OptionRelative.EXCLUDED)) {
//            HashSet<Option> topSet = findTop(targetOption);
//            for (Option tariffOptionTop : topSet) {
//                realRelatedOptions = generateRealRelatedOptionList(tariffOptionTop.getRelatedOptions());
//                realRelatedOptions.add(tariffOptionTop);
//                realRelatedOptions.add(addedOption);
//            }
//            targetOption.getExcludedOptions().add(addedOption);
//            addedOption.getExcludedOptions().add(targetOption);
//        }
        OptionRelative rule = OptionRelative.valueOf(addRelativeToOption.getOptionRelative());
        Option targetOption = optionDao.findOptionById(addRelativeToOption.getTargetOptionId());
        Option addedOption = optionDao.findOptionById(addRelativeToOption.getAddedOptionId());

        if (rule.equals(OptionRelative.RELATED)){

            //            Add relative
            targetOption.getRelatedOptions().add(addedOption);
            addedOption.getLeadOptions().add(targetOption);
//            Add relative for each option related for addedOption and vice versa
            addedOption.getRelatedOptions().forEach(option -> targetOption.getRelatedOptions().add(option));
            addedOption.getRelatedOptions().forEach(option -> option.getLeadOptions().add(targetOption));
//            Add excluding option from target list into added list and vice versa))
            targetOption.getExcludedOptions().forEach(option -> addedOption.getExcludedOptions().add(option));
//            Add lead option for added and added for each lead option for target
            targetOption.getLeadOptions().forEach(option -> option.getRelatedOptions().add(addedOption));
            targetOption.getLeadOptions().forEach(option -> addedOption.getLeadOptions().add(option));
        }

        if (rule.equals(OptionRelative.EXCLUDED)){

            targetOption.getExcludedOptions().add(addedOption);
            addedOption.getExcludedOptions().add(targetOption);

        }
    }

    @Override
    public void delRelativeToOption(DelRelativeFromOption delRelativeFromOption) throws DatabaseException, MyException {
        OptionRelative relative = OptionRelative.valueOf(delRelativeFromOption.getOptionRelative());

        Option targetOption = optionDao.findOptionById(delRelativeFromOption.getTargetOptionId());
        Option deletedOption = optionDao.findOptionById(delRelativeFromOption.getAddedOptionId());

        if (relative.equals(OptionRelative.EXCLUDED)){

            targetOption.getExcludedOptions().remove(deletedOption);
            deletedOption.getExcludedOptions().remove(targetOption);
        }

        if (relative.equals(OptionRelative.RELATED)){

            targetOption.getRelatedOptions().remove(deletedOption);
            deletedOption.getLeadOptions().remove(targetOption);
        }
    }

    public List<OptionDto> mapToOptionDtoList(List<Option> baseList){
        List<OptionDto> resultList = new ArrayList<>();
        baseList.forEach(option->resultList.add(modelMapper.map(option, OptionDto.class)));
        return resultList;
    }

//    private HashSet<Option> findTop(Option tariffOption) {
//        HashSet<Option> topSet = new HashSet<>();
//
//        anotherIsRelatedFor(tariffOption, topSet);
//        return topSet;
//    }
//
//    private void anotherIsRelatedFor(Option anotherIsRelatedFor, HashSet<Option> topSet) {
//
//        if (anotherIsRelatedFor.getLeadOptions().isEmpty()) {
//
//            topSet.add(anotherIsRelatedFor);
//        }
//
//        for (Option isRelatedFor : anotherIsRelatedFor.getLeadOptions()) {
//            anotherIsRelatedFor(isRelatedFor, topSet);
//        }
//    }
//
//    private HashSet<Option> generateRealRelatedOptionList (Set<Option> anotherRelatedOptionList) {
//        HashSet<Option> realRelatedOptions = new HashSet<>();
//        anotherRelatedFor(anotherRelatedOptionList, realRelatedOptions);
//        return realRelatedOptions;
//    }
//
//    private void anotherRelatedFor(Set<Option> anotherRelatedOptionList, HashSet<Option> realRelatedOptions) {
//        realRelatedOptions.addAll(anotherRelatedOptionList);
//        for (Option tariffOption : anotherRelatedOptionList) {
//            anotherRelatedFor(tariffOption.getRelatedOptions(), realRelatedOptions);
//        }
//    }
}
