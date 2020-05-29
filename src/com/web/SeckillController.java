package com.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.Exposer;
import com.dto.SeckillExecution;
import com.dto.SeckillResult;
import com.entity.Seckill;
import com.enums.SeckillStatEnum;
import com.exception.RepeatKillException;
import com.exception.SeckillCloseException;
import com.service.SeckillService;

/**
 * @author wenbochang
 * @date 2018��1��13��
 */

@Controller
//@RequestMapping("seckill") // ģ�� ���е�url���� /seckill/{id}/ϸ�� ������ʽ��
public class SeckillController {

	@Autowired
	private SeckillService seckillService;

	/**
	 * ��ȡ��ɱ���е��б�ҳ��
	 * 
	 * ��ȡ����������mapҲ������model.addAttrabute ���Լ�ϰ������map����
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Map<String, Object> map) {

		// ��ȡ�б�ҳ
		List<Seckill> list = seckillService.getSeckillList();
		map.put("list", list);

		return "list";
	}

	/**
	 * ��ȡ����ҳ��
	 * 
	 * @PathVariable("seckillId") ����������һ��Ҫ��
	 * 
	 * @param map
	 * @param seckillId
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(Map<String, Object> map, 
			@PathVariable("seckillId") Long seckillId) {

		if (seckillId == null) {
			return "redirect:/list";
		}
		// ��ȡ����ҳ��
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "redirect:/list";
			// return "forward:/seckill/list";
		}
		map.put("seckill", seckill);

		return "detail";
	}

	/**
	 * ajax�ӿ� ����json
	 * 
	 * �Լ���˵�¶�ResponseBody���ע������
	 * 
	 * �ٷ����ͣ���Ӧ������Ӧ�ñ�ֱ��д�ص�HTTP��Ӧ�壨��response��body�У���ȥ
	 * 
	 * �Լ���⣺���Ǿֲ��������ݣ��������ݵĴ��� ֱ��д��response��body�У���������Ӧͷȫ�����䡣
	 * 
	 * 2018��1��14��17:04:54 �����Լ������˲��ԣ����ؾ��Ǿ���json����
	 * ǰ��ֱ���ü��ɣ���ǿ
	 * 
	 * �������һ��������http://localhost:8080/seckill/time/now��
	 * ���ʲ�������Ⱦ��ֱ�ӷ���json����ǰ�˵���Աʹ�ã����������ķ���ܶ���
	 * {"success":true,"data":1515921153359,"error":null}
	 * 
	 * @param seckillId
	 * @return
	 */

	@RequestMapping(value = "/{seckillId}/exposer", 
			//method = RequestMethod.POST, 
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(
			@PathVariable("seckillId") Long seckillId) {

		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", 
			//method = RequestMethod.POST, 
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(
			@PathVariable("seckillId") Long seckillId, 
			@PathVariable("md5") String md5,
			@CookieValue(value = "killPhone", required = false) Long phone) {
		
		// springmvc valid �Ժ�ѧһ�� spring����֤��Ϣ
		if (phone == null) {
			return new SeckillResult<SeckillExecution>(false, "δע��");
		}

		SeckillResult<SeckillExecution> result;
		try {
			SeckillExecution execution = seckillService.executeSeckillProduce(seckillId, phone, md5);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatKillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}
		return result;
	}
	
	/**
	 * ���գ� prproduces = {"application/json;charest=UTF-8"})
	 * charest=UTF-8  	�Ⱥ��м䲻���пո��пո�ͱ� http500����
	 * �������˹��ˣ��Ҳ�
	 * 
	 * @return
	 */
	@RequestMapping(value = "/time/now", method = RequestMethod.GET,
			produces = {"application/json;charest=UTF-8"})
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}











