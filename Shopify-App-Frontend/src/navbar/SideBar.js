import React from "react";
import {
  HomeMinor,
  OrdersMinor,
  ProductsMinor,
  CustomersMinor,
  LogOutMinor,
} from "@shopify/polaris-icons";
import { Navigation } from "@shopify/polaris";

import { useState } from "react";

// import { useNavigate } from "react-router-dom";

export const SideNavMarkup = () => {
  // const nav = useNavigate();

  return (
    <Navigation location="/">
      <Navigation.Section
        items={[
          {
            url: "/home",
            label: "Home",
            icon: HomeMinor,
          },
          {
            url: "/orders",
            excludePaths: ["#"],
            label: "Orders",
            icon: OrdersMinor,
            badge: "15",
          },
          {
            url: "/products",
            //onClick: { navigateProduct },
            label: "Products",
            icon: ProductsMinor,
          },
          {
            url: "/customers",
            //onClick: { navigateProduct },
            label: "Customer",
            icon: CustomersMinor,
          },
          {
            url: "/logOut",
            //onClick: { navigateProduct },
            label: "LogOut",
            icon: LogOutMinor,
          },
        ]}
      />
    </Navigation>
  );
};
