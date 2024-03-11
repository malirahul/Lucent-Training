import React, { useEffect, useState } from "react";
import { AlphaCard, Box, Button, DataTable, Page } from "@shopify/polaris";
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
  SkeletonBodyText,
} from "@shopify/polaris";
import moment from "moment";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";

export default function ViewCustomer() {
  const [customers, setData] = useState([]);
  const { id } = useParams();

  const navigate = useNavigate();

  // const SkeletonLabel = (props) => {
  //   return (
  //     <Box
  //       background="surface-neutral"
  //       minHeight="1rem"
  //       maxWidth="5rem"
  //       borderRadius="base"
  //       {...props}
  //     />
  //   );
  // };
  useEffect(() => {
    loadCustomers();
  }, []);

  console.log("Token : ", sessionStorage.getItem("authToken"));
  const loadCustomers = async () => {
    try {
      const result = await axios.get(`http://localhost:3000/customers/${id}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + sessionStorage.getItem("authToken"),
        },
      });
      setData(result.data);
      console.log(result.data);
    } catch (error) {
      console.error(error);
    }
  };

  const date = new Date(customers.created_at);
  const formattedDate = moment(date).format("MMMM DD, YYYY [at] h:mm A");

  function addOrderForm() {
    console.log("Going add Order");
    navigate("/createOrder");
  }

  return (
    <Page
      breadcrumbs={[{ content: "Customers", url: "/customers" }]}
      title={customers.first_name + " " + customers.last_name}
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
              <HorizontalGrid columns={{ xs: 1, md: 2 }}>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <div className="container">
                    <DataTable
                      columnContentTypes={["text"]}
                      headings={[
                        <span style={{ fontWeight: "bold" }}>
                          Amount Spent
                        </span>,
                      ]}
                      rows={[["₹" + customers.total_spent]]}
                    />
                  </div>
                </Box>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <div className="container">
                    <DataTable
                      columnContentTypes={["text"]}
                      headings={[
                        <span style={{ fontWeight: "bold" }}>Order</span>,
                      ]}
                      rows={[[customers.orders_count]]}
                    />
                  </div>
                </Box>
              </HorizontalGrid>
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
                  Last order placed
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <HorizontalGrid columns={{ xs: 1, md: 2 }}>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <span
                    style={{
                      color: "blue",
                      cursor: "pointer",
                      fontWeight: "normal",
                      float: "left",
                      fontSize: "16px",
                    }}
                  >
                    {customers.last_order_name}
                  </span>
                  <br />
                  <span style={{ fontWeight: "normal", float: "left" }}>
                    {formattedDate}
                  </span>
                </Box>
                <Box border="divider" borderRadius="base" minHeight="0.5rem">
                  <span style={{ fontWeight: "normal" }}>
                    {customers.total_spent}
                  </span>
                </Box>
                <Box border="divider" borderRadius="base" minHeight="10rem" />
              </HorizontalGrid>
            </VerticalStack>
            <Divider />
            <br />
            <div
              style={{
                display: "flex",
                justifyContent: "flex-end",
                alignItems: "center",
              }}
            >
              <span style={{ marginRight: "1rem" }}>View all Orders</span>

              <Button
                onClick={() => {
                  addOrderForm();
                }}
                primary
                style={{ marginRight: "1px" }}
              >
                Create Order
              </Button>
            </div>
          </AlphaCard>
        </VerticalStack>
        <VerticalStack gap={{ xs: "4", md: "2" }}>
          <AlphaCard roundedAbove="sm">
            <VerticalStack gap="4">
              <span
                style={{
                  fontWeight: "bold",
                  float: "left",
                  fontSize: "16px",
                  marginRight: "auto",
                }}
              >
                Notes
              </span>
              <span
                style={{
                  fontWeight: "Normal",
                  float: "left",
                  fontSize: "14px",
                  marginRight: "auto",
                }}
              >
                This customer doesn’t have notes.
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
                  Customer
                </span>
                <SkeletonDisplayText size="small" />
              </div>
              <div style={{ marginRight: "auto" }}>
                {["Has a classic account"]}
              </div>
              <Divider />
              <span
                style={{
                  fontWeight: "bold",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                Contact information
              </span>
              <span
                style={{
                  color: "blue",
                  cursor: "pointer",
                  fontWeight: "normal",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                {customers.email}
              </span>
              <span
                style={{
                  fontWeight: "normal",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                {customers.phone}
              </span>

              <span
                style={{
                  fontWeight: "bolder",
                  marginRight: "auto",
                  fontSize: "12px",
                }}
              >
                Will receive notifications in English
              </span>
              <Divider />
              <span
                style={{
                  fontWeight: "bolder",
                  fontSize: "14px",
                  marginRight: "auto",
                }}
              >
                Default Address
              </span>
              <span
                style={{
                  fontWeight: "normal",
                  marginRight: "auto",
                  fontSize: "14px",
                }}
              >
                {customers && customers.addresses && customers.addresses[0] && (
                  <span
                    style={{
                      fontWeight: "normal",
                      float: "auto",
                      fontSize: "14px",
                    }}
                  >
                    {customers.addresses[0].name}
                    <br />
                    {customers.addresses[0].company}
                    <br />
                    {customers.addresses[0].city}
                    <br />
                    {customers.addresses[0].province}
                    <br />
                    {customers.addresses[0].country_name}
                    <br />
                    {customers.addresses[0].phone}
                  </span>
                )}
              </span>

              {/* <Box border="divider" borderRadius="base" minHeight="10rem" />
              <Box>
                <Bleed marginInline={{ xs: 4, sm: 5 }}>
                  <Divider />
                </Bleed>
              </Box>
              <SkeletonLabel />
              <Divider />
              <SkeletonBodyText /> */}
            </VerticalStack>
          </AlphaCard>
        </VerticalStack>
      </HorizontalGrid>
    </Page>
  );
}
