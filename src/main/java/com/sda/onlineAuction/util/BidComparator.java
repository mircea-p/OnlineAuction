package com.sda.onlineAuction.util;


import com.sda.onlineAuction.model.Bid;

import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {

    @Override
    public int compare(Bid bid1, Bid bid2){
        if(bid1.getValue().compareTo(bid2.getValue()) < 0){   //sau asa
            return -1;
        }else if(bid1.getValue() > bid2.getValue()){               // sau asa, e acelasi lucru.
            return 1;
        }
        return 0;
    }
}
