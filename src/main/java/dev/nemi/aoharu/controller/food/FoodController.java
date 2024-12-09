package dev.nemi.aoharu.controller.food;

import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.dto.food.FoodEditDTO;
import dev.nemi.aoharu.dto.food.FoodPageRequestDTO;
import dev.nemi.aoharu.dto.food.FoodRegisterDTO;
import dev.nemi.aoharu.dto.food.FoodViewDTO;
import dev.nemi.aoharu.service.food.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/food")
  public String index(
    @Valid @ModelAttribute("requestDTO") FoodPageRequestDTO requestDTO,
    BindingResult requestBR,
    Model model
    ) {
    if (requestBR.hasErrors()) return "redirect:/food";
    log.info(requestDTO);
//    PageResponseDTO<FoodViewDTO> responseDTO = foodService.getFoods(requestDTO);
    PageResponseDTO<FoodViewDTO> responseDTO = foodService.getFoods(requestDTO);
    model.addAttribute("requestDTO", requestDTO);
    model.addAttribute("responseDTO", responseDTO);
    return "food/index";
  }

  @GetMapping("/food/view/{id}")
  public String view(
    @Valid @ModelAttribute("requestDTO") FoodPageRequestDTO requestDTO,
    BindingResult requestBR,
    @PathVariable long id, Model model) {
    if (requestBR.hasErrors()) return "redirect:/food/view/"+id;
    FoodViewDTO food = foodService.getOne(id);
    model.addAttribute("food", food);
    return "food/view";
  }

  @GetMapping("/food/register")
  public String register(
    @Valid @ModelAttribute("requestDTO") FoodPageRequestDTO requestDTO,
    BindingResult requestBR,
    Model model
  ) {
    if (requestBR.hasErrors()) return "redirect:/food/register";
    model.addAttribute("pageAttr", Map.of("edit", false, "title", "Register", "action", "/food/register"));
    return "food/edit";
  }

  @PostMapping("/food/register")
  public String register(
    @Valid FoodRegisterDTO registerDTO,
    BindingResult registerBR,
    RedirectAttributes redirectAttributes
  ) {
    if (registerBR.hasErrors()) {
      redirectAttributes.addFlashAttribute("food", registerDTO);
      redirectAttributes.addFlashAttribute("invalid", registerBR.getAllErrors());
      return "redirect:/food/register";
    }
    Long id = foodService.register(registerDTO);
    return "redirect:/food/view/" + id;
  }

  @GetMapping("/food/edit/{id}")
  public String edit(
    @Valid @ModelAttribute("requestDTO") FoodPageRequestDTO requestDTO,
    BindingResult requestBR,
    @PathVariable long id, Model model
  ) {
    if (requestBR.hasErrors()) return "redirect:/food/edit/"+id;
    FoodViewDTO food = foodService.getOne(id);
    model.addAttribute("food", food);
    model.addAttribute("pageAttr", Map.of("edit", true, "title", "Edit", "action", "/food/edit"));
    return "food/edit";
  }

  @PostMapping("/food/edit")
  public String edit(
    @Valid FoodEditDTO editDTO,
    BindingResult editBR,
    RedirectAttributes redirectAttributes,
    FoodPageRequestDTO requestDTO
  ) {
    if (editBR.hasErrors()) {
      redirectAttributes.addFlashAttribute("food", editDTO);
      redirectAttributes.addFlashAttribute("invalid", editBR.getAllErrors());
      return "redirect:/food/edit/" + editDTO.getId() + requestDTO.useQuery();
    }
    foodService.edit(editDTO);
    return "redirect:/food/view/" + editDTO.getId() + requestDTO.useQuery() ;
  }
}
