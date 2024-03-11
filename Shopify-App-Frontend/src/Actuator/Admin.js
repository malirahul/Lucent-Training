import React, { useState, useEffect } from "react";
import {
  Card,
  Layout,
  ProgressBar,
  Button,
  Page,
  Label,
  HorizontalGrid,
  Spinner,
  Grid,
  LegacyCard,
  Divider,
  DataTable,
  Pagination,
  Box,
  AlphaCard,
} from "@shopify/polaris";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  PieChart,
  Pie,
  Cell,
  ResponsiveContainer,
} from "recharts";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faExclamationTriangle,
  faExclamationCircle,
  faServer,
} from "@fortawesome/free-solid-svg-icons";

import "./assert/style.css";
import { CardBody } from "reactstrap";

export default function Admin() {
  const [usage, setUsage] = useState([]);
  const [refreshing, setRefreshing] = useState(false);
  const [servers, setServers] = useState([]);
  const [trace, setTrace] = useState([]);

  useEffect(() => {
    fetchCpuUsage();
    checkServerRes();
    fetchHttpTraces();
  }, []);

  //Process CPU Usage Process

  const fetchCpuUsage = async () => {
    setRefreshing(true);
    const response = await axios.get(
      "http://localhost:3000/actuator/metrics/process.cpu.usage"
    );

    setUsage(response.data);
    console.log(response.data);
    setRefreshing(false);
  };

  let memoryData = 0;
  if (usage.measurements) {
    memoryData = usage.measurements[0].value * 100;
  }
  const memoryUsed = memoryData ? `${memoryData.toFixed(2)} %` : "Loading...";
  const memoryLeft = memoryData
    ? `${(100 - memoryData).toFixed(2)} %`
    : "Loading...";
  const progressBarFill = memoryData ? memoryData / 1 : 0;
  console.log(progressBarFill);

  const handleRefresh = () => {
    // Reload the current page, but only the progress bar.
    window.location.reload(true, { progressBar: true });
  };

  //Total Http Traces Response Count

  const types = ["status:200", "status:404"];

  const checkServerRes = async () => {
    try {
      const serverData = [];
      for (const type of types) {
        const response = await axios.get(
          "http://localhost:3000/actuator/metrics/http.server.requests?tag=" +
            type
        );
        console.error(response.status);

        const totalCount = response.data.measurements[0].value;
        console.log(totalCount);
        serverData.push(totalCount);
      }
      setServers(serverData);
      console.log(serverData);
    } catch (error) {
      console.log(error);
    }
  };

  function httpTracesCount(type) {
    let resCount = 0;
    if (!type) {
      return resCount;
    } else {
      resCount = type;
    }
    return resCount;
  }

  //Http Traces Details
  const fetchHttpTraces = async () => {
    setRefreshing(true);
    const response = await axios.get(
      "http://localhost:3000/actuator/httpexchanges"
    );

    setTrace(response.data.exchanges);
    console.log(response.data.exchanges);
    setRefreshing(false);
  };

  console.log(trace);

  let currentTime = trace && trace.length > 0 ? trace[0].timestamp : new Date();
  function getCurrentTime() {
    const formattedTime = new Date(currentTime).toLocaleString("en-IN"); // Adjust the format as desired
    return formattedTime;
  }

  // data for the bar chart or for the pie chart
  const statusData = [
    { name: "200", value: servers[0] },
    { name: "404", value: servers[1] },
    { name: "400", value: servers[2] },
    { name: "500", value: servers[3] },
  ];

  const COLORS = ["#28a745", "#0088FE", "#FFBB28", "#dc3545"];

  // const handleView = (trace) => {
  //   // Do something with the trace
  // };

  //Pagination
  const [currentPage, setCurrentPage] = useState(1);
  const tracesPerPage = 10;

  const indexOfLastTrace = currentPage * tracesPerPage;
  const indexOfFirstTrace = indexOfLastTrace - tracesPerPage;
  const currentTraces = trace.slice(indexOfFirstTrace, indexOfLastTrace);

  const handlePrevious = () => {
    setCurrentPage(currentPage - 1);
  };

  const handleNext = () => {
    setCurrentPage(currentPage + 1);
  };

  return (
    <Page fullWidth>
      <Layout>
        <Layout.Section>
          <AlphaCard>
            <LegacyCard>
              <Label>
                <b>Process CPU Usage</b>
              </Label>
              <div
                style={{
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <ProgressBar
                  progress={progressBarFill}
                  label="Memory Usage"
                  max={100}
                />
                <Label>
                  <strong>Memory Used</strong>
                  <br />
                  <span>{memoryUsed}</span>
                </Label>
                <Label>
                  <strong>Memory Left</strong>
                  <br />
                  <span>{memoryLeft}</span>
                </Label>
                <Button onClick={handleRefresh} disabled={refreshing}>
                  {refreshing ? "Refreshing..." : "Refresh"}
                </Button>
              </div>
            </LegacyCard>
          </AlphaCard>
        </Layout.Section>

        <Layout.Section>
          <AlphaCard>
            <div className="container">
              <div className="card-body">
                <h4 className="card-title">
                  <span className="response-text">
                    <b>Http Response</b>
                  </span>
                </h4>
                <Grid>
                  <Grid.Cell
                    columnSpan={{ xs: 12, sm: 6, md: 6, lg: 3, xl: 3 }}
                  >
                    <LegacyCard sectioned>
                      <div className="card success-card">
                        <div className="card-body">
                          <div className="card-response-data-circle">
                            {httpTracesCount(servers[0])}
                          </div>
                          <h6 className="card-title">
                            <FontAwesomeIcon icon={faCheckCircle} />
                            <span className="response-text">200 Responses</span>
                          </h6>
                          <p className="card-text">
                            Updated:{getCurrentTime()}
                          </p>
                        </div>
                      </div>
                    </LegacyCard>
                  </Grid.Cell>
                  <Grid.Cell
                    columnSpan={{ xs: 12, sm: 6, md: 6, lg: 3, xl: 3 }}
                  >
                    <LegacyCard sectioned style={{ height: "200px" }}>
                      <div className="card error-card">
                        <div className="card-body">
                          <div className="card-response-data-circle">
                            {httpTracesCount(servers[1])}
                          </div>
                          <h6 className="card-title">
                            <FontAwesomeIcon icon={faExclamationCircle} />
                            <span className="response-text">404 Responses</span>
                          </h6>
                          <p className="card-text">
                            Updated : {getCurrentTime()}
                          </p>
                        </div>
                      </div>
                    </LegacyCard>
                  </Grid.Cell>
                  <Grid.Cell
                    columnSpan={{ xs: 12, sm: 6, md: 6, lg: 3, xl: 3 }}
                  >
                    <LegacyCard sectioned>
                      <div className="card warning-card">
                        <div className="card-body">
                          <div className="card-response-data-circle">
                            {httpTracesCount(servers[2])}
                          </div>
                          <h6 className="card-title">
                            <FontAwesomeIcon icon={faExclamationTriangle} />
                            <span className="response-text">400 Responses</span>
                          </h6>
                          <p className="card-text">
                            Updated : {getCurrentTime()}
                          </p>
                        </div>
                      </div>
                    </LegacyCard>
                  </Grid.Cell>
                  <Grid.Cell
                    columnSpan={{ xs: 12, sm: 6, md: 6, lg: 3, xl: 3 }}
                  >
                    <LegacyCard sectioned>
                      <div className="card info-card">
                        <div className="card-body">
                          <div className="card-response-data-circle">
                            {httpTracesCount(servers[3])}
                          </div>
                          <h6 className="card-title">
                            <FontAwesomeIcon icon={faServer} />
                            <span className="response-text">500 Responses</span>
                          </h6>
                          <p className="card-text">
                            Updated : {getCurrentTime()}
                          </p>
                        </div>
                      </div>
                    </LegacyCard>
                  </Grid.Cell>
                </Grid>
              </div>
            </div>
          </AlphaCard>
        </Layout.Section>

        <Layout.Section>
          <AlphaCard title="Request Count">
            <div className="container">
              <div className="card-body">
                <h4 className="card-title">
                  <span className="response-text">
                    <b>Http Response</b>
                  </span>
                </h4>
                <Grid style={{ display: "flex", justifyContent: "center" }}>
                  <Grid.Cell columnSpan={{ xs: 6, sm: 6, md: 6, lg: 6, xl: 6 }}>
                    <LegacyCard title="Bar Chart" sectioned>
                      <ResponsiveContainer width="100%" height={350}>
                        <BarChart data={statusData}>
                          <CartesianGrid strokeDasharray="4 4" />
                          <XAxis dataKey="name" />
                          <YAxis nameKey="value" />
                          <Tooltip />
                          <Legend />
                          <Bar dataKey="value" fill="#8884d8" label>
                            {statusData.map((entry, index) => (
                              <Cell
                                key={`cell-${index}`}
                                fill={COLORS[index % COLORS.length]}
                              />
                            ))}
                          </Bar>
                        </BarChart>
                      </ResponsiveContainer>
                    </LegacyCard>
                  </Grid.Cell>
                  <Grid.Cell columnSpan={{ xs: 6, sm: 6, md: 6, lg: 6, xl: 6 }}>
                    <LegacyCard title="Pie Chart" sectioned>
                      <ResponsiveContainer width="100%" height={350}>
                        <PieChart
                          width="100%"
                          height="40%"
                          alignContent="center"
                        >
                          <Tooltip />
                          <Legend />
                          <Pie
                            data={statusData}
                            dataKey="value"
                            nameKey="name"
                            cx="50%"
                            cy="50%"
                            outerRadius={80}
                            fill="#8884d8"
                            label
                          >
                            {statusData.map((entry, index) => (
                              <Cell
                                key={`cell-${index}`}
                                fill={COLORS[index % COLORS.length]}
                              />
                            ))}
                          </Pie>
                          <Legend />
                        </PieChart>
                      </ResponsiveContainer>
                    </LegacyCard>
                  </Grid.Cell>
                </Grid>
              </div>
            </div>
          </AlphaCard>
        </Layout.Section>

        <Layout.Section>
          <AlphaCard title="Http Traces">
            <div className="container">
              <div className="card-body">
                <h4 className="card-title">
                  <span className="response-text">
                    <b>Http Traces</b>
                  </span>

                  <span className="pageable">
                    <Pagination
                      hasPrevious={currentPage > 1}
                      onPrevious={handlePrevious}
                      hasNext={currentTraces.length === tracesPerPage}
                      onNext={handleNext}
                    />
                  </span>
                </h4>
                <DataTable
                  columnContentTypes={[
                    "numeric",
                    "text",
                    "text",
                    "numeric",
                    "text",
                    "text",
                    // "text",
                  ]}
                  headings={[
                    "#",
                    "Timestamp",
                    "Method",
                    "Time Taken (ms)",
                    "Status",
                    "URI",
                    // "View",
                  ]}
                  rows={currentTraces.map((tr, index) => [
                    indexOfFirstTrace + index + 1,
                    new Date(tr.timestamp).toLocaleString("en-IN"),
                    tr.request.method,
                    tr.timeTaken,
                    tr.response.status,
                    tr.request.uri,
                    // <Button onClick={() => handleView(tr)}>View</Button>,
                  ])}
                />
              </div>
            </div>
          </AlphaCard>
        </Layout.Section>
      </Layout>
    </Page>
  );
}
