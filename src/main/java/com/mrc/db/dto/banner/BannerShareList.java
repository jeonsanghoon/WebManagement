package com.mrc.db.dto.banner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author kyh
 *
 * 2020.10.19
 */

@SuppressWarnings("serial")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerShareList implements Serializable {
    private List<BannerRequest> shares;
    private Integer totalNum;
}
