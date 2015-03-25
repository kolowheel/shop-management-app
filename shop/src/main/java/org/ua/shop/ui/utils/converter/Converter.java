package org.ua.shop.ui.utils.converter;

import javafx.collections.ObservableList;
import org.joda.time.LocalDateTime;
import org.ua.shop.dto.*;
import org.ua.shop.ui.dto.IncomeGoodsTableCell;
import org.ua.shop.ui.dto.OutcomeGoodTableCell;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yaroslav.Gryniuk
 */
public class Converter {
    public IncomeTrans convert(User user, List<IncomeGoodsTableCell> cells) {
        IncomeTrans trans = new IncomeTrans();
        trans.setDate(LocalDateTime.now());
        trans.setUser(user);
        trans.setIncomeGoods(cells.stream().map(cell -> {
            TransGood good = new TransGood();
            Good good1 = new Good();
            good1.setId(Integer.parseInt(cell.getCode()));
            good.setCount(Integer.parseInt(cell.getCount()));
            good.setGood(good1);
            good.setPrice(new BigDecimal(cell.getPerItemPrice()));
            return good;
        }).collect(Collectors.toList()));
        trans.setProvider(cells.get(0).getOrg());
        return trans;
    }

    public OutcomeTrans convert(User user, ObservableList<OutcomeGoodTableCell> cells) {
        OutcomeTrans trans = new OutcomeTrans();
        trans.setUser(user);
        trans.setDate(LocalDateTime.now());
        trans.setOutcomeGoods(cells.stream().map(cell -> {
            TransGood good = new TransGood();
            Good good1 = new Good();
            good1.setId(Integer.parseInt(cell.getCode()));
            good.setCount(Integer.parseInt(cell.getCount()));
            good.setGood(good1);
            good.setPrice(new BigDecimal(cell.getPerItemPrice()));
            return good;
        }).collect(Collectors.toList()));
        return trans;
    }
}
