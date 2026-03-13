import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [message, setMessage] = useState('Loading...')

  useEffect(() => {
    fetch('/api/hello')
      .then(res => {
        if (!res.ok) {
          throw new Error(`Request failed: ${res.status}`)
        }
        return res.text()
      })
      .then(text => setMessage(text))
      .catch(() => setMessage('Could not reach backend.'))
  }, [])

  return (
    <>
      <section id="center">
        <div>
          <h1>{message}</h1>
        </div>
      </section>

    </>
  )
}

export default App
