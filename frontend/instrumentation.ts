import { registerOTel } from '@vercel/otel'
import { OTLPTraceExporter } from "@opentelemetry/exporter-trace-otlp-http"

export function register() {
  registerOTel({
    serviceName: 'frontend',
    traceExporter: new OTLPTraceExporter({
      url: process.env.OTEL_EXPORTER_OTLP_LOGS_ENDPOINT
    })
  })
}
