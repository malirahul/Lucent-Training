import React, { useEffect, useState } from "react";
import { AlphaCard, Box, Button, Page, Select } from "@shopify/polaris";
import {
  DuplicateMinor,
  ArchiveMinor,
  DeleteMinor,
} from "@shopify/polaris-icons";
import {
  HorizontalGrid,
  VerticalStack,
  SkeletonDisplayText,
  Bleed,
  Divider,
} from "@shopify/polaris";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

export default function AddProduct() {
  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [vendor, setVendor] = useState("");
  const [status, setStatus] = useState("");
  const [product_type, setProductType] = useState("");
  const [variants, setVariants] = useState("");
  // const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [compare_at_price, setCompareAtPrice] = useState("");
  const [tags, setTags] = useState("");
  const [options1, setOptions1] = useState("");

  const [colorVariant, setColorVariant] = useState("");
  const [priceVariant, setPriceVariant] = useState("");
  const [materialVariant, setMaterialVariant] = useState("");

  const handleAddProduct = async (event) => {
    event.preventDefault();
    try {
      const result = await axios.post(
        "http://localhost:3000/createProduct",
        {
          title: title,
          vendor: vendor,
          product_type: product_type,
          status: status,
          // description: description,
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
        setVariants("");
        // setDescription("");
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
      title="Add Product"
      secondaryActions={[
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
                    placeholder="Short sleeve t-shirt"
                    onChange={(event) => setTitle(event.target.value)}
                  />
                </div>
                <div className="container">
                  <label htmlFor="description" style={{ marginRight: "100%" }}>
                    Description
                  </label>
                  <textarea
                    id="description"
                    // value={description}
                    type="text"
                    // onChange={(event) => setDescription(event.target.value)}
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
                      placeholder="₹ 0.00"
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
                      placeholder="₹ 0.00"
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
                    placeholder="Select"
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
                  placeholder="Select"
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
                    placeholder="Product Type"
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
                    placeholder="Vendor"
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
                    placeholder="Tags"
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

      <Button primary submit onClick={handleAddProduct}>
        Add Product
      </Button>
      <Divider />
    </Page>
  );
}

// import React, { useState } from "react";
// import { Form, FormLayout, TextField, Select, Button } from "@shopify/polaris";
// import axios from "axios";
// import { useNavigate } from "react-router-dom";

// function AddProduct() {
//   const navigate = useNavigate();

//   const [title, setTitle] = useState("");
//   const [vendor, setVendor] = useState("");
//   const [status, setStatus] = useState("");
//   const [product_type, setproductType] = useState("");
//   const [variant, setVariant] = useState("");

//   const [colorVariant, setColorVariant] = useState("");
//   const [priceVariant, setPriceVariant] = useState("");
//   const [materialVariant, setMaterialVariant] = useState("");

//   const handleAddProduct = async (event) => {
//     event.preventDefault();
//     try {
//       const result = await axios.post(
//         "http://localhost:3000/createProduct",
//         {
//           title: title,
//           vendor: vendor,
//           status: status,
//           product_type: product_type,
//           variants: [
//             {
//               status: status,
//               option1: variant,
//             },
//           ],
//         },
//         {
//           headers: {
//             "Content-Type": "application/json",
//             Authorization: "Bearer " + sessionStorage.getItem("authToken"),
//           },
//         }
//       );
//       console.log("Result", result);
//       // Handle the response from Shopify's API
//       if (result && result.status === 200) {
//         console.log("Product created successfully!");
//         // Clear the form fields
//         setTitle("");
//         setVendor("");
//         setStatus("");
//         setproductType("");
//         setVariant("");
//         navigate("/products");
//       } else {
//         console.error("Error creating product:", result && result.statusText);
//       }
//     } catch (error) {
//       console.error(error);
//     }
//   };

//   return (
//     <Form>
//       <FormLayout>
//         <TextField
//           label="Title"
//           value={title}
//           onChange={(value) => setTitle(value)}
//         />
//         <TextField
//           label="Vendor"
//           value={vendor}
//           onChange={(value) => setVendor(value)}
//         />
//         <Select
//           label="Status"
//           value={status}
//           options={[
//             { label: "active", value: "active" },
//             { label: "draft", value: "draft" },
//             { label: "finished", value: "finished" },
//           ]}
//           onChange={(value) => setStatus(value)}
//         />
//         <TextField
//           label="ProductType"
//           value={product_type}
//           onChange={(value) => setproductType(value)}
//         />
//         <Select
//           label="Variant"
//           options={[
//             { label: "Option1", value: "Option1" },
//             { label: "Option2", value: "Option2" },
//             { label: "Option3", value: "Option3" },
//           ]}
//           value={variant}
//           onChange={(value) => setVariant(value)}
//         />
//         {variant === "Color" && (
//           <Select
//             label="Color"
//             options={[
//               { label: "Red", value: "Red" },
//               { label: "Blue", value: "Blue" },
//               { label: "Green", value: "Green" },
//               // Add more color options here
//             ]}
//             value={colorVariant}
//             onChange={(value) => setColorVariant(value)}
//           />
//         )}
//         {variant === "Price" && (
//           <Select>
//             <TextField
//               label="Price"
//               value={priceVariant}
//               onChange={(value) => setPriceVariant(value)}
//             />
//           </Select>
//         )}
//         {variant === "Material" && (
//           <Select
//             label="Material"
//             options={[
//               { label: "Wood", value: "Wood" },
//               { label: "Metal", value: "Metal" },
//               { label: "Plastic", value: "Plastic" },
//               // Add more material options here
//             ]}
//             value={materialVariant}
//             onChange={(value) => setMaterialVariant(value)}
//           />
//         )}
//         <Button primary submit onClick={handleAddProduct}>
//           Add Product
//         </Button>
//       </FormLayout>
//     </Form>
//   );
// }

// export default AddProduct;
