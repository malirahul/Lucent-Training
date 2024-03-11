import React, { useEffect, useState } from "react";
import { AlphaCard, Box, Button, Page, Select } from "@shopify/polaris";
import {
  DuplicateMinor,
  ArchiveMinor,
  DeleteMinor,
  UpdateInventoryMajor,
} from "@shopify/polaris-icons";
import {
  HorizontalGrid,
  VerticalStack,
  SkeletonDisplayText,
  Bleed,
  Divider,
  SkeletonBodyText,
} from "@shopify/polaris";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

export default function ViewProduct() {
  const [products, setData] = useState([]);
  const { id } = useParams();

  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [vendor, setVendor] = useState("");
  const [status, setStatus] = useState("");
  const [product_type, setProductType] = useState("");
  // const [variants, setVariants] = useState("");
  const [body_html, setBodyHtml] = useState("");
  const [price, setPrice] = useState("");
  const [compare_at_price, setCompareAtPrice] = useState("");
  const [tags, setTags] = useState("");
  const [options1, setOptions1] = useState("");

  const [colorVariant, setColorVariant] = useState("");
  const [priceVariant, setPriceVariant] = useState("");
  const [materialVariant, setMaterialVariant] = useState("");

  useEffect(() => {
    loadProducts();
  }, []);

  console.log("Token : ", sessionStorage.getItem("authToken"));
  const loadProducts = async () => {
    try {
      const response = await axios.get(`http://localhost:3000/products/${id}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + sessionStorage.getItem("authToken"),
        },
      });

      const product = response.data;
      setTitle(product.title);
      setBodyHtml(product.body_html);
      setVendor(product.vendor);
      setStatus(product.status);
      setProductType(product.product_type);
      setOptions1(product.variants[0].option1);
      setPrice(product.variants[0].price);
      setCompareAtPrice(product.variants[0].compare_at_price);
      setTags(product.tags);
    } catch (error) {
      console.error(error);
    }
  };

  const handleEditProduct = async (event) => {
    event.preventDefault();
    try {
      const result = await axios.put(
        "http://localhost:3000/updateProduct/" + id,
        {
          title: title,
          vendor: vendor,
          product_type: product_type,
          status: status,
          body_html: body_html,
          tags: tags,

          variants: [
            {
              price: price,
              option1: options1,
              compare_at_price: compare_at_price,
            },
          ],
          //options: [{}],
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + sessionStorage.getItem("authToken"),
          },
        }
      );
      console.log("Result", result);
      // Handle the response from Shopify's API
      if (result && result.status === 200) {
        console.log("Product created successfully!");
        // Clear the form fields
        setTitle("");
        setVendor("");
        setStatus("");
        setProductType("");
        // setVariants("");
        setBodyHtml("");
        setPrice("");
        setCompareAtPrice("");
        setTags("");
        setOptions1("");

        navigate("/products");
      } else {
        console.error("Error creating product:", result && result.statusText);
      }
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Page
      breadcrumbs={[{ content: "Products", url: "/products" }]}
      title={products.title}
      secondaryActions={[
        {
          content: "Update Product",
          icon: UpdateInventoryMajor,
          accessibilityLabel: "Update Product",
          onAction: handleEditProduct,
        },

        {
          content: "Duplicate",
          icon: DuplicateMinor,
          accessibilityLabel: "Secondary action label",
          onAction: () => alert("Duplicate action"),
        },
        {
          content: "Archive",
          icon: ArchiveMinor,
          accessibilityLabel: "Secondary action label",
          onAction: () => alert("Archive action"),
        },
        {
          content: "Delete",
          icon: DeleteMinor,
          destructive: true,
          accessibilityLabel: "Secondary action label",
          onAction: () => alert("Delete action"),
        },
      ]}
      pagination={{
        hasPrevious: true,
        hasNext: true,
      }}
    >
      <HorizontalGrid columns={{ xs: 1, md: "2fr 1fr" }} gap="4">
        <VerticalStack gap="4">
          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              {/* <SkeletonDisplayText size="small" /> */}
              <Box border="divider" borderRadius="base" minHeight="0.5rem">
                <div className="container">
                  <label htmlFor="title" style={{ marginRight: "100%" }}>
                    Title
                  </label>
                  <input
                    id="title"
                    type="text"
                    value={title}
                    onChange={(event) => setTitle(event.target.value)}
                  />
                </div>
                <div className="container">
                  <label htmlFor="body_html" style={{ marginRight: "100%" }}>
                    Description
                  </label>
                  <textarea
                    id="body_html"
                    value={body_html}
                    type="text"
                    onChange={(event) => setBodyHtml(event.target.value)}
                    style={{
                      height: "150px",
                      width: "100%",
                      resize: "vertical",
                    }}
                  />
                </div>
              </Box>
            </VerticalStack>
          </AlphaCard>

          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <div style={{ display: "flex", alignItems: "center" }}>
                <span
                  style={{
                    fontWeight: "bold",
                    marginRight: "8px",
                    fontSize: "16px",
                  }}
                >
                  Pricing
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <HorizontalGrid columns={{ xs: 1, md: 2 }}>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <div className="container">
                    <label htmlFor="price">Price</label>
                    <input
                      id="price"
                      type="number"
                      value={price}
                      fontWeight="normal"
                      style={{ height: "30px" }}
                      onChange={(event) => setPrice(event.target.value)}
                    />
                  </div>
                  <br />
                </Box>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <div className="container">
                    <label htmlFor="compare_at_price">Compare At Price</label>
                    <input
                      id="compare_at_price"
                      type="number"
                      value={compare_at_price}
                      fontWeight="normal"
                      style={{ height: "30px" }}
                      onChange={(event) =>
                        setCompareAtPrice(event.target.value)
                      }
                    />
                  </div>
                  <br />
                </Box>
              </HorizontalGrid>
            </VerticalStack>
            <Divider />
            <br />
          </AlphaCard>
          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <div style={{ display: "flex", alignItems: "center" }}>
                <span
                  style={{
                    fontWeight: "bold",
                    marginRight: "8px",
                    fontSize: "16px",
                  }}
                >
                  Variants
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <Divider />
              <HorizontalGrid columns={{ xs: 1, md: 2 }}>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <label style={{ marginRight: "100%" }}>Variants</label>
                  <Select
                    options={[
                      { label: "Option1", value: "Option1" },
                      { label: "Option2", value: "Option2" },
                      { label: "Option3", value: "Option3" },
                    ]}
                    value={options1}
                    onChange={(selected) => setOptions1(selected)}
                  />
                </Box>
              </HorizontalGrid>
            </VerticalStack>
          </AlphaCard>
        </VerticalStack>

        <VerticalStack gap={{ xs: "4", md: "2" }}>
          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <Box border="divider" borderRadius="base" minHeight="0.5rem">
                <label style={{ marginRight: "100%" }}>Status</label>
                <Select
                  options={[
                    { label: "active", value: "active" },
                    { label: "draft", value: "draft" },
                    { label: "archived", value: "archived" },
                  ]}
                  value={status}
                  onChange={(selected) => setStatus(selected)}
                />
              </Box>
            </VerticalStack>
          </AlphaCard>

          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <div style={{ display: "flex", alignItems: "center" }}>
                <span
                  style={{
                    fontWeight: "bold",
                    marginRight: "8px",
                    fontSize: "16px",
                  }}
                >
                  Publishing
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <span style={{ marginRight: "auto", fontWeight: "bold" }}>
                {["Sales channels"]}
              </span>

              <span
                style={{
                  fontWeight: "normal",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                <ul>
                  <li>Online Store</li>
                  <li>training_app23 and Shopify GraphiQL App</li>
                </ul>
              </span>

              <Divider />
              <span
                style={{
                  fontWeight: "bold",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                Market
              </span>
              <span
                style={{
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                <ul>
                  <li>Incomplete India, International, and Mexico</li>
                </ul>
              </span>
            </VerticalStack>
          </AlphaCard>
          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <div style={{ display: "flex", alignItems: "center" }}>
                <span
                  style={{
                    fontWeight: "bold",
                    marginRight: "8px",
                    fontSize: "16px",
                  }}
                >
                  Product organnization
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <Box border="divider" borderRadius="base" minHeight="0.5rem">
                <label style={{ marginRight: "50%", fontWeight: "normal" }}>
                  Product category
                </label>
                <Select
                  placeholder="Select"
                  type="text"
                  options={[
                    {
                      label: "Arts & Entertainment",
                      value: "Arts & Entertainment",
                    },
                    {
                      label: "Animals & Pet Supplies",
                      value: "Animals & Pet Supplies",
                    },
                    { label: "Cameras & Optics", value: "Cameras & Optics" },
                    {
                      label: "Apparel & Accessories",
                      value: "Apparel & Accessories",
                    },
                    {
                      label: "Food, Beverages & Tobacco",
                      value: "Food, Beverages & Tobacco",
                    },
                    { label: "Electronics", value: "Electronics" },
                  ]}
                />
                <br />
                <div>
                  <label
                    htmlFor="product_type"
                    style={{ fontWeight: "normal", marginRight: "10rem" }}
                  >
                    Product Type
                  </label>
                  <input
                    id="product_type"
                    type="text"
                    value={product_type}
                    onChange={(event) => setProductType(event.target.value)}
                  />
                  <label
                    htmlFor="vendor"
                    style={{ fontWeight: "normal", marginRight: "10rem" }}
                  >
                    Vendor
                  </label>
                  <input
                    id="vendor"
                    type="text"
                    value={vendor}
                    onChange={(event) => setVendor(event.target.value)}
                  />

                  <label
                    htmlFor="tags"
                    style={{ fontWeight: "normal", marginRight: "10rem" }}
                  >
                    Tags
                  </label>
                  <input
                    id="tags"
                    type="text"
                    value={tags}
                    onChange={(event) => setTags(event.target.value)}
                  />
                </div>
              </Box>
            </VerticalStack>
          </AlphaCard>
        </VerticalStack>
      </HorizontalGrid>
      <br />
      <Divider />
      <Divider />
      <br />
    </Page>
  );
}
