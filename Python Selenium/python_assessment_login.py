import pytest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time, openpyxl

@pytest.mark.all
def test_url_title():
    driver = webdriver.Chrome()
    driver.maximize_window()
    driver.get("https://www.saucedemo.com/")
    print(f"Title of the page is {driver.title}")
    driver.close()

def read_data():
    data_dict = {}
    data = openpyxl.load_workbook("login_credentials.xlsx")
    data = data.active
    for i in range(2, 4):
        data_dict[data.cell(i, 1). value] = data.cell(i, 2). value
    return data_dict

@pytest.mark.all
def test_successful_login():
    driver = webdriver.Chrome()
    driver.maximize_window()
    driver.get("https://www.saucedemo.com/")
    data_dict = read_data()
    user = driver.find_element(By.XPATH, '//*[@id="user-name"]')
    user.send_keys(list(data_dict.keys())[0])
    passwd = driver.find_element(By.XPATH, '//*[@id="password"]')
    passwd.send_keys(list(data_dict.values())[0])
    driver.find_element(By.XPATH, '//*[@id="login-button"]').click()
    print(f"After successful login, the title of the page is {driver.title}")
    driver.close()


@pytest.mark.all
def test_unsuccessful_login():
    driver = webdriver.Chrome()
    driver.maximize_window()
    driver.get("https://www.saucedemo.com/")
    data_dict = read_data()
    user = driver.find_element(By.XPATH, '//*[@id="user-name"]')
    user.send_keys(list(data_dict.keys())[1])
    passwd = driver.find_element(By.XPATH, '//*[@id="password"]')
    passwd.send_keys(list(data_dict.values())[1])
    driver.find_element(By.XPATH, '//*[@id="login-button"]').click()
    err_text = driver.find_element(By.XPATH, '//*[@class="error-message-container error"]/h3')
    print(err_text.text)
    print(f"After successful login, the title of the page is {driver.title}")
    driver.close()
