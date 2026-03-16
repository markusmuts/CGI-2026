import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [tables, setTables] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    const loadTables = async () => {
      setLoading(true)
      setError('')

      try {
        const res = await fetch('/api/tables')

        if (!res.ok) {
          const status = `${res.status}${res.statusText ? ` ${res.statusText}` : ''}`
          const contentType = res.headers.get('content-type') || ''
          let detail = ''

          if (contentType.includes('application/json')) {
            const body = await res.json()
            detail = body?.message || body?.error || ''
          } else {
            detail = await res.text()
          }

          const trimmedDetail = typeof detail === 'string' ? detail.trim() : ''
          throw new Error(
            `${status}${trimmedDetail ? `: ${trimmedDetail}` : ''}`
          )
        }

        const data = await res.json()
        setTables(Array.isArray(data) ? data : [])
      } catch (err) {
        setError(
          err instanceof Error
            ? err.message
            : 'Unknown error occurred.'
        )
      } finally {
        setLoading(false)
      }
    }

    loadTables()
  }, [])

  return (
    <>
      <section id="center">
        <div>
          <h1>CGI Restaurant</h1>
          <p>Restaurant tables</p>
          {loading ? (
            <p>Loading tables...</p>
          ) : error ? (
            <p className="error-message" role="alert">
              <span className="error-icon" aria-hidden="true">X</span>
              <span>{error}</span>
            </p>
          ) : tables.length === 0 ? (
            <p>No tables found.</p>
          ) : (
            <ul>
              {tables.map(table => (
                <li key={table.id}>
                  Table #{table.tableNumber} | Capacity: {table.capacity}
                </li>
              ))}
            </ul>
          )}
        </div>
      </section>

    </>
  )
}

export default App
