import "./App.css";

import "@shopify/polaris/build/esm/styles.css";

import {
  Routes,
  Route,
  BrowserRouter,
  Link as ReactRouterLink,
  useNavigate,
} from "react-router-dom";

import { AppProvider, Frame } from "@shopify/polaris";

import { TopBarMarkup } from "./navbar/TopBar";
import { SideNavMarkup } from "./navbar/SideBar";
import Dashboard from "./components/Dashboard";
import LogOut from "./components/LogOut";
import Products from "./components/ProductComponent/Products";
import Customer from "./components/CustomerComponent/Customer";
import AddProduct from "./components/ProductComponent/AddProduct";
import AddCustomer from "./components/CustomerComponent/AddCustomer";
import Orders from "./components/OrderComponent/Orders";
import EditCustomer from "./components/CustomerComponent/EditCustomer";
import CreateOrder from "./components/OrderComponent/CreateOrder";
import EditProduct from "./components/ProductComponent/EditProduct";
import ViewProduct from "./components/ProductComponent/ViewProduct";
import ViewCustomer from "./components/CustomerComponent/ViewCustomer";
import Success from "./components/Success";
import Profile from "./Actuator/Profile";
import Admin from "./Actuator/Admin";
import HealthComponent from "./Actuator/HealthComponent";
import MetricsComponents from "./Actuator/MetricsComponent";

function App() {
  return (
    <div className="App">
      <AppProvider linkComponent={Link}>
        <BrowserRouter>
          <Frame topBar={TopBarMarkup()} navigation={SideNavMarkup()}>
            <Routes>
              <Route path="" element={<Success />} />
              //Monitoring
              <Route path="/admin" element={<Admin />} />
              <Route path="/health" element={<HealthComponent />} />
              <Route path="/metrics" element={<MetricsComponents />} />
              <Route path="/profile" element={<Profile />} />
              //HomePage
              <Route path="/home" element={<Dashboard />} />
              //Product Crud
              <Route path="/products" element={<Products />} />
              <Route path="/addProduct" element={<AddProduct />} />
              <Route
                path="/product/viewProduct/:id"
                element={<ViewProduct />}
              />
              <Route
                path="/product/editProduct/:id"
                element={<EditProduct />}
              />
              //Customer Crud
              <Route path="/customers" element={<Customer />} />
              <Route path="/addCustomer" element={<AddCustomer />} />
              <Route
                path="/customer/editcustomer/:id"
                element={<EditCustomer />}
              />
              <Route
                path="/customer/viewCustomer/:id"
                element={<ViewCustomer />}
              />
              //Order Crud
              <Route path="/orders" element={<Orders />} />
              <Route path="/createOrder" element={<CreateOrder />} />
              <Route path="/logOut" element={<LogOut />} />
            </Routes>
          </Frame>
        </BrowserRouter>
      </AppProvider>
    </div>
  );
}

const IS_EXTERNAL_LINK_REGEX = /^(?:[a-z][a-z\d+.-]*:|\/\/)/;

function Link({ children, url = "", external, ref, ...rest }) {
  if (external || IS_EXTERNAL_LINK_REGEX.test(url)) {
    rest.target = "_blank";

    rest.rel = "noopener noreferrer";

    return (
      <a href={url} {...rest}>
        {children}
      </a>
    );
  }

  return (
    <ReactRouterLink to={url} {...rest}>
      {children}
    </ReactRouterLink>
  );
}

export default App;
