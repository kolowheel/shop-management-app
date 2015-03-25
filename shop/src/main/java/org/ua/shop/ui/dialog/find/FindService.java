package org.ua.shop.ui.dialog.find;

import org.ua.shop.api.context.ShopAppContext;
import org.ua.shop.dto.Good;
import org.ua.shop.ui.utils.PopUpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaroslav.Gryniuk
 */
public interface FindService {
    public static FindService FIND_BY_NAME_SERVICE = input -> ShopAppContext.getInstance().getGoodsService().findGoodsByName(input);
    public static FindService FIND_BY_ID_SERVICE = input -> {
        List<Good> goods = new ArrayList<>();
        try {
            int inputAsInt = Integer.valueOf(input);
            Good good = ShopAppContext.getInstance().getGoodsService().findGoodById(inputAsInt);
            if (good != null) {
                goods.add(good);
            }
        } catch (Exception e) {
            PopUpUtils.showBadNumberFormatPopUp();
        }
        return goods;
    };

    List<Good> find(String input);
}
