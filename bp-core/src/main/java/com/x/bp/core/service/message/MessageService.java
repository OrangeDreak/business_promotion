package com.x.bp.core.service.message;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.enums.EnumTextTrans;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.core.dto.message.MessageDTO;
import com.x.bp.core.dto.message.MessagePageReq;
import com.x.bp.core.utils.EmailUtil;
import com.x.bp.dao.mapper.MessageMapper;
import com.x.bp.dao.po.MessageDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private EmailUtil emailUtil;

    public void addMessage(MessageDTO dto) {
        if (StringUtils.isBlank(dto.getContent()) && StringUtils.isBlank(dto.getTitle())) {
            throw new CommonBizException(EnumError.PARAMETER_ERROR);
        }
        MessageDO messageDO = new MessageDO();
        BeanUtils.copyProperties(dto, messageDO);
        messageMapper.insert(messageDO);
        emailUtil.sendText2OfficialNoThrows(EnumTextTrans.USER_PRIVATE_MESSAGE, dto.getName(), dto.getEmail(), dto.getTitle(), dto.getContent());

    }

    public ServicePageResult<MessageDTO> list(MessagePageReq req) {
        LambdaQueryWrapper<MessageDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageDO::getIsDelete, 0);
        queryWrapper.orderByDesc(MessageDO::getId);
        IPage<MessageDO> iPage = messageMapper.selectPage(new Page<>(req.getPageNo(), req.getPageSize()), queryWrapper);
        if (null == iPage) {
            return ServicePageResult.buildSuccess(new ArrayList<>(), 0);
        }
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return ServicePageResult.buildSuccess(new ArrayList<>(), iPage.getTotal());
        }
        List<MessageDTO> messageDTOS = new ArrayList<>();
        iPage.getRecords().forEach(msg -> {
            MessageDTO messageDTO = new MessageDTO();
            BeanUtils.copyProperties(msg, messageDTO);
            messageDTOS.add(messageDTO);
        });
        return ServicePageResult.buildSuccess(messageDTOS, iPage.getTotal());
    }

}
