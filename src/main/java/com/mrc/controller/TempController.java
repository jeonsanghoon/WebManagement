package com.mrc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TempController extends BaseController {
	@RequestMapping(value = "/temp", method = RequestMethod.GET)
	public String temp(Model model, String error, String logout) {
		return "./pages/temp/templeate01";
	}
}
