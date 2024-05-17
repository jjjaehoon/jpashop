package com.jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	/**
	 * Model
	 */
	@GetMapping("members/new")
	public String createForm(Model model) {
		model.addAttribute("memberForm", new MemberForm());
		//1인자 화면으로 넘어갈 때 2인자 데이터를 실어서 넘김

		return "members/createMemberForm";
	}

	/**
	 * 인자에 @Valid 옵션을 주면 NotEmpty, NotNull 등등을 알아서 체크해서 확인함
	 *
	 * @Valid가 있는 상태에서 BindingResult가 있으면 오류가 담겨서 create 메서드가 다시 실행이 됨
	 */
	@PostMapping("members/new")
	public String create(@Valid MemberForm form, BindingResult result) {

		if (result.hasErrors()) {
			return "members/createMemberForm";
		}

		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);

		memberService.join(member);

		return "redirect:/"; //첫 번째 페이지로 리다이렉트
	}

	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);

		return "members/memberList";
	}
}
