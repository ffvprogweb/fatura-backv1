package com.fatec.fatura.sistema;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

@UsePlaywright
class TSReq16EmissaoDaFaturaTests {
	// Shared between all tests in this class.
	static Playwright playwright;
	static Browser browser;

	
	@Test
	  void ct01_cadastrar_fatura(Page page) {
		System.out.println(">>>>>> teste iniciado ");
	    page.navigate("http://localhost:3000/");
	    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Menu Fatura")).click();
	    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cadastrar")).click();
	    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("c:/edson/evidencia/sig_cadastra_fatura1.png")));
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("CNPJ:")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("CNPJ:")).fill("04269717000194");
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Data de Vencimento:")).fill("2025-05-30");
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Serviço:")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Serviço:")).fill("teste");
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Valor (R$):")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Valor (R$):")).fill("1000");
	    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Confirmar")).click();
	    PlaywrightAssertions.assertThat(page.getByText("Fatura cadastrada com sucesso!")).isVisible();
	    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("c:/edson/evidencia/sig_cadastra_fatura2.png")));
	  }

}
