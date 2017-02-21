import sys

_python_major_version, _python_minor_version = sys.version_info[0], sys.version_info[1]

if _python_major_version == 2 and (_python_minor_version == 6 or _python_minor_version == 7):
    import mock
elif _python_major_version == 3 and (_python_minor_version == 4 or _python_minor_version == 5):
    from unittest import mock
else:
    raise ImportError('Python version {} is not currently supported.'.format(sys.version.split(' ')[0]))
