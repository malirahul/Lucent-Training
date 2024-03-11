import {
  Listbox,
  Combobox,
  Icon,
  Layout,
  Page,
  LegacyCard,
  DataTable,
  TextField,
} from "@shopify/polaris";
import { SearchMinor } from "@shopify/polaris-icons";
import axios from "axios";
import { useState, useCallback, useMemo, useEffect } from "react";

export default function CreateOrder() {
  const [products, setProducts] = useState([]);
  const [selectedOption, setSelectedOption] = useState("");
  const [inputValue, setInputValue] = useState("");
  const [options, setOptions] = useState([]);
  const [rows, setRows] = useState([]);
  const [data, setData] = useState();

  function QuantityField() {
    const [quant, setQuant] = useState(1);

    const handleQuantityChange = (newQuant) => {
      if (newQuant <= 0) {
        setQuant(1);
      } else {
        setQuant(newQuant);
      }
    };
    return (
      <div>
        <TextField
          type="number"
          value={quant.toString()}
          onChange={handleQuantityChange}
        />
      </div>
    );
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get("http://localhost:3000/products", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        });
        setProducts(response.data);
        setData(response.data);
        setOptions(response.data);
      } catch (error) {
        console.log(error);
      }
    };

    const fetchCustomerData = async () => {
      try {
        const response = await axios.get("http://localhost:3000/customers", {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        });
        setCustomers(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
    fetchCustomerData();
  }, []);

  const productOptions = useMemo(() => {
    return products.map((data) => ({
      value: data.title,
      label: data.title,
    }));
  }, [products]);

  const updateProductText = useCallback(
    (value) => {
      setInputValue(value);

      if (value === "") {
        setOptions(productOptions);
        return;
      }

      const filterRegex = new RegExp(value, "i");
      const resultOptions = productOptions.filter((option) =>
        option.label.match(filterRegex)
      );
      setOptions(resultOptions);
    },
    [productOptions]
  );
  const updateProductSelection = useCallback((selected) => {
    console.log("selected", selected);
    const matchedOption = data.find((option) => {
      return option.id === selected;
    });

    console.log("matchedOption", matchedOption);

    const row = matchedOption?.variants?.map((product) => {
      const title = product.title;
      const price = parseFloat(product.price) || 0; // ensure price is a number
      const quantity = <QuantityField />;

      const total = price * quantity;

      return [title, price, quantity, total];
    });

    setSelectedOption(selected);
    setRows(row);
  });

  const optionsProductMarkup =
    products.length > 0
      ? products.map((option) => {
          const { title, id } = option;

          return (
            <Listbox.Option
              key={`${id}`}
              value={id}
              selected={selectedOption === id}
              accessibilityLabel={title}
            >
              {title}
            </Listbox.Option>
          );
        })
      : null;

  //customer
  const [customers, setCustomers] = useState([]);
  const [cusSelectedOption, setCusSelectedOption] = useState("");
  const [cusInputValue, setCusInputValue] = useState("");
  const [cusOptions, setCusOptions] = useState([]);

  const customerOptions = useMemo(() => {
    return customers.map((data) => ({
      value: data.first_name + " " + data.last_name,
      label: data.first_name + " " + data.last_name,
    }));
  }, [customers]);

  const updateCustomerText = useCallback(
    (value) => {
      setCusInputValue(value);

      if (value === "") {
        setCusOptions(customerOptions);
        return;
      }

      const filterRegex = new RegExp(value, "i");
      const resultOptions = customerOptions.filter((option) =>
        option.label.match(filterRegex)
      );
      setCusOptions(resultOptions);
    },
    [customerOptions]
  );

  const updateCustomerSelection = useCallback(
    (selected) => {
      const matchedOption = cusOptions.find((option) => {
        return option.value.match(selected);
      });

      setCusSelectedOption(selected);
      setCusInputValue((matchedOption && matchedOption.label) || "");
    },
    [cusOptions]
  );

  const optionsCustomerMarkup =
    cusOptions.length > 0
      ? cusOptions.map((option) => {
          const { label, value } = option;

          return (
            <Listbox.Option
              key={`${value}`}
              value={value}
              selected={cusSelectedOption === value}
              accessibilityLabel={label}
            >
              {label}
            </Listbox.Option>
          );
        })
      : null;

  return (
    <div>
      <div>
        <Page fullWidth>
          <Layout>
            <Layout.Section>
              <LegacyCard title="Products" sectioned>
                <Combobox
                  activator={
                    <Combobox.TextField
                      prefix={<Icon source={SearchMinor} />}
                      onChange={updateProductText}
                      label="Search tags"
                      labelHidden
                      value={inputValue}
                      placeholder="Search tags"
                      autoComplete="off"
                    />
                  }
                >
                  {products.length > 0 ? (
                    <Listbox onSelect={updateProductSelection}>
                      {optionsProductMarkup}
                    </Listbox>
                  ) : null}
                </Combobox>
                <DataTable
                  columnContentTypes={["text", "numeric", "numeric", "numeric"]}
                  headings={["Product", "price", "Quantity", "Total"]}
                  rows={rows}
                  totals={["", "", "", ""]}
                />
              </LegacyCard>
            </Layout.Section>
            <Layout.Section secondary>
              <LegacyCard title="Customers" sectioned>
                <Combobox
                  activator={
                    <Combobox.TextField
                      prefix={<Icon source={SearchMinor} />}
                      onChange={updateCustomerText}
                      label="Search tags"
                      labelHidden
                      value={cusInputValue}
                      placeholder="Search tags"
                      autoComplete="off"
                    />
                  }
                >
                  {cusOptions.length > 0 ? (
                    <Listbox onSelect={updateCustomerSelection}>
                      {optionsCustomerMarkup}
                    </Listbox>
                  ) : null}
                </Combobox>
              </LegacyCard>
            </Layout.Section>
          </Layout>
        </Page>
      </div>

      <div></div>
    </div>
  );
}
